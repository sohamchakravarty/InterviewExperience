package com.interview.legacy.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String id;
    private final String customerId;
    private final String externalId;
    private final OrderStatus status;
    private final String currency;
    private final double total;
    private final long createdAtEpochMillis;
    private final String paymentReference;
    private final boolean expedite;
    private final String promoCode;
    private final List<OrderItem> items;

    public Order(
        String id,
        String customerId,
        String externalId,
        OrderStatus status,
        String currency,
        double total,
        long createdAtEpochMillis,
        String paymentReference,
        boolean expedite,
        String promoCode,
        List<OrderItem> items
    ) {
        this.id = id;
        this.customerId = customerId;
        this.externalId = externalId;
        this.status = status;
        this.currency = currency;
        this.total = total;
        this.createdAtEpochMillis = createdAtEpochMillis;
        this.paymentReference = paymentReference;
        this.expedite = expedite;
        this.promoCode = promoCode;
        this.items = new ArrayList<>(items);
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getExternalId() {
        return externalId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
    }

    public double getTotal() {
        return total;
    }

    public long getCreatedAtEpochMillis() {
        return createdAtEpochMillis;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public boolean isExpedite() {
        return expedite;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"").append(id).append("\",");
        json.append("\"customerId\":\"").append(customerId).append("\",");
        json.append("\"externalId\":\"").append(externalId).append("\",");
        json.append("\"status\":\"").append(status).append("\",");
        json.append("\"currency\":\"").append(currency).append("\",");
        json.append("\"total\":").append(total).append(",");
        json.append("\"createdAtEpochMillis\":").append(createdAtEpochMillis).append(",");
        json.append("\"paymentReference\":\"").append(paymentReference).append("\",");
        json.append("\"expedite\":").append(expedite).append(",");
        json.append("\"promoCode\":\"").append(promoCode).append("\",");
        json.append("\"items\":[");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                json.append(",");
            }
            json.append(items.get(i).toJson());
        }
        json.append("]}");
        return json.toString();
    }
}
