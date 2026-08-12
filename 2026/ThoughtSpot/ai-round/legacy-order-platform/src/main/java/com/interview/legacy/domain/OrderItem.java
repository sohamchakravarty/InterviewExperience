package com.interview.legacy.domain;

public class OrderItem {
    private final String sku;
    private final int quantity;
    private final double unitPrice;

    public OrderItem(String sku, int quantity, double unitPrice) {
        this.sku = sku;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String toJson() {
        return "{\"sku\":\"" + sku + "\",\"quantity\":" + quantity + ",\"unitPrice\":" + unitPrice + "}";
    }
}
