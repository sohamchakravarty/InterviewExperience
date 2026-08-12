package com.interview.legacy.support;

import com.interview.legacy.domain.Customer;
import com.interview.legacy.domain.Order;
import com.interview.legacy.domain.OrderRequest;
import com.interview.legacy.domain.OrderStatus;
import com.interview.legacy.domain.PaymentReceipt;
import com.interview.legacy.integration.FraudCheckClient;
import com.interview.legacy.integration.InventoryClient;
import com.interview.legacy.integration.NotificationClient;
import com.interview.legacy.integration.PaymentGatewayClient;
import com.interview.legacy.persistence.CustomerRepository;
import com.interview.legacy.persistence.OrderEventStore;
import com.interview.legacy.persistence.OrderRepository;
import com.interview.legacy.persistence.RequestRepository;

import java.util.List;
import java.util.UUID;

public class OrderService {
    private final RequestRepository requestRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final FraudCheckClient fraudCheckClient;
    private final InventoryClient inventoryClient;
    private final PaymentGatewayClient paymentGatewayClient;
    private final NotificationClient notificationClient;
    private final ExpiringOrderCache recentOrders;
    private final AuditTrail auditTrail;
    private final OrderEventStore orderEventStore;

    public OrderService(
        RequestRepository requestRepository,
        OrderRepository orderRepository,
        CustomerRepository customerRepository,
        FraudCheckClient fraudCheckClient,
        InventoryClient inventoryClient,
        PaymentGatewayClient paymentGatewayClient,
        NotificationClient notificationClient,
        ExpiringOrderCache recentOrders,
        AuditTrail auditTrail,
        OrderEventStore orderEventStore
    ) {
        this.requestRepository = requestRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.fraudCheckClient = fraudCheckClient;
        this.inventoryClient = inventoryClient;
        this.paymentGatewayClient = paymentGatewayClient;
        this.notificationClient = notificationClient;
        this.recentOrders = recentOrders;
        this.auditTrail = auditTrail;
        this.orderEventStore = orderEventStore;
    }

    public synchronized Order placeOrder(String requestId, OrderRequest request) {
        if (request == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("items are required");
        }

        // check if there already exists an order for the same request in RequestRepository to ensure idempotency
        Order existingOrder = requestRepository.findOrderById(requestId);
        if (existingOrder != null) {
            return existingOrder;
        }

        if (!blank(request.getExternalId())) {
            Order cached = recentOrders.get(request.getCustomerId() + ":" + request.getExternalId());
            if (cached != null) {
                auditTrail.record("cache-hit externalId=" + request.getExternalId() + " requestId=" + requestId);
                return cached;
            }
        }

        Customer customer = customerRepository.findById(request.getCustomerId());
        double total = PricingEngine.calculateTotal(request, customer);
        boolean approved = fraudCheckClient.isAllowed(customer, request, total);
        boolean reserved = inventoryClient.reserve(request.getItems());

        if (!approved) {
            auditTrail.record("order-rejected customerId=" + customer.getId() + " requestId=" + requestId);
            orderEventStore.append("REJECTED|customerId=" + customer.getId() + "|reason=fraud|payload=" + request.getRawPayload());
            throw new IllegalStateException("order rejected by fraud engine");
        }
        if (!reserved) {
            auditTrail.record("inventory-shortage customerId=" + customer.getId() + " requestId=" + requestId);
            orderEventStore.append(
                "REJECTED|customerId=" + customer.getId() + "|reason=inventory|payload=" + request.getRawPayload()
            );
            throw new IllegalStateException("inventory unavailable");
        }

        PaymentReceipt receipt = paymentGatewayClient.charge(customer, total, request.getCurrency());
        Order order = new Order(
            UUID.randomUUID().toString(),
            request.getCustomerId(),
            fallback(request.getExternalId(), "generated-" + System.nanoTime()),
            OrderStatus.CREATED,
            request.getCurrency(),
            total,
            System.currentTimeMillis(),
            receipt.getConfirmationId(),
            request.isExpedite(),
            request.getPromoCode(),
            request.getItems()
        );

        orderRepository.save(order);
        requestRepository.recordRequest(requestId, order);

        recentOrders.put(order.getCustomerId() + ":" + order.getExternalId(), order);
        auditTrail.record(
            "order-created orderId=" + order.getId() + " requestId=" + requestId + " raw=" + request.getRawPayload()
        );
        orderEventStore.append(
            "CREATED|orderId=" + order.getId() + "|paymentRef=" + order.getPaymentReference() + "|payload=" + request.getRawPayload()
        );
        notificationClient.sendOrderConfirmation(order, customer);
        return order;
    }

    public String listOrdersAsJson() {
        List<Order> orders = orderRepository.findAll();
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < orders.size(); i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(orders.get(i).toJson());
        }
        builder.append("]");
        return builder.toString();
    }

    public int orderCount() {
        return orderRepository.size();
    }

    public int cacheSize() {
        return recentOrders.size();
    }

    private boolean blank(String value) {
        return value == null || value.isBlank();
    }

    private String fallback(String value, String fallback) {
        return blank(value) ? fallback : value;
    }
}
