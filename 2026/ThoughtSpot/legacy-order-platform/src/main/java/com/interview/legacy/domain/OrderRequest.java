package com.interview.legacy.domain;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    private final String customerId;
    private final String externalId;
    private final String currency;
    private final boolean expedite;
    private final String promoCode;
    private final List<OrderItem> items;
    private final String rawPayload;

    public OrderRequest(
        String customerId,
        String externalId,
        String currency,
        boolean expedite,
        String promoCode,
        List<OrderItem> items,
        String rawPayload
    ) {
        this.customerId = customerId;
        this.externalId = externalId;
        this.currency = currency;
        this.expedite = expedite;
        this.promoCode = promoCode;
        this.items = new ArrayList<>(items);
        this.rawPayload = rawPayload;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getCurrency() {
        return currency;
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

    public String getRawPayload() {
        return rawPayload;
    }
}
