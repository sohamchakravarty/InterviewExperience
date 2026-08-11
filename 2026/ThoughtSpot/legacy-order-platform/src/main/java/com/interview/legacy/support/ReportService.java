package com.interview.legacy.support;

import com.interview.legacy.domain.Order;
import com.interview.legacy.domain.PaymentReceipt;
import com.interview.legacy.integration.PaymentGatewayClient;
import com.interview.legacy.persistence.OrderRepository;
import java.util.List;

public class ReportService {
    private final OrderRepository orderRepository;
    private final PaymentGatewayClient paymentGatewayClient;
    private final AuditTrail auditTrail;
    private final String reportTitle;

    public ReportService(
        OrderRepository orderRepository,
        PaymentGatewayClient paymentGatewayClient,
        AuditTrail auditTrail,
        String reportTitle
    ) {
        this.orderRepository = orderRepository;
        this.paymentGatewayClient = paymentGatewayClient;
        this.auditTrail = auditTrail;
        this.reportTitle = reportTitle;
    }

    public String generateCsvReport() {
        List<Order> orders = orderRepository.findAll();
        StringBuilder builder = new StringBuilder();
        builder.append("# ").append(reportTitle).append("\n");
        builder.append("id,customerId,status,total,currency,createdAt,paymentReference,chargedAmount\n");
        for (Order order : orders) {
            PaymentReceipt receipt = paymentGatewayClient.lookup(order.getPaymentReference());
            builder.append(order.getId()).append(",");
            builder.append(order.getCustomerId()).append(",");
            builder.append(order.getStatus()).append(",");
            builder.append(order.getTotal()).append(",");
            builder.append(order.getCurrency()).append(",");
            builder.append(order.getCreatedAtEpochMillis()).append(",");
            builder.append(order.getPaymentReference()).append(",");
            builder.append(receipt.getAmount()).append("\n");
        }
        auditTrail.record("report-generated rows=" + orders.size());
        return builder.toString();
    }
}
