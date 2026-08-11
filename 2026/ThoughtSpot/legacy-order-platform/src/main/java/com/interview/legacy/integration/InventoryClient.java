package com.interview.legacy.integration;

import com.interview.legacy.domain.OrderItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryClient {
    private final Map<String, Integer> availableUnits = new HashMap<>();

    public InventoryClient() {
        availableUnits.put("A-100", 100);
        availableUnits.put("B-200", 50);
        availableUnits.put("C-300", 4);
        availableUnits.put("D-400", 0);
    }

    public boolean reserve(List<OrderItem> items) {
        sleep(80);
        for (OrderItem item : items) {
            int current = availableUnits.getOrDefault(item.getSku(), 0);
            if (current < item.getQuantity()) {
                return false;
            }
            availableUnits.put(item.getSku(), current - item.getQuantity());
        }
        return true;
    }

    public int currentUnits(String sku) {
        return availableUnits.getOrDefault(sku, 0);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
