package com.interview.legacy.support;

import com.interview.legacy.domain.Customer;
import com.interview.legacy.domain.OrderItem;
import com.interview.legacy.domain.OrderRequest;
import java.util.HashMap;
import java.util.Map;

public final class PricingEngine {
    private static final Map<String, Double> TAX_RATES = new HashMap<>();

    static {
        TAX_RATES.put("US", 0.0825);
        TAX_RATES.put("EU", 0.20);
        TAX_RATES.put("APAC", 0.12);
    }

    private PricingEngine() {
    }

    public static double calculateTotal(OrderRequest request, Customer customer) {
        double subtotal = 0.0;
        for (OrderItem item : request.getItems()) {
            subtotal += item.getUnitPrice() * item.getQuantity();
        }

        double shipping = 0.0;
        if (customer.isVip()) {
            subtotal = subtotal * 0.93;
        }
        if ("SAVE10".equalsIgnoreCase(request.getPromoCode())) {
            subtotal = subtotal - 10.0;
        } else if ("BULK5".equalsIgnoreCase(request.getPromoCode()) && request.getItems().size() >= 3) {
            subtotal = subtotal * 0.95;
        } else if (!"FREESHIP".equalsIgnoreCase(request.getPromoCode())) {
            shipping = request.isExpedite() ? 24.99 : 7.99;
        }

        double taxRate = TAX_RATES.getOrDefault(customer.getRegion(), 0.09);
        double tax = subtotal * taxRate;
        return subtotal + shipping + tax;
    }
}
