package com.interview.legacy.persistence;

import com.interview.legacy.domain.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {
    private final List<Order> orders = new ArrayList<>();
    private final Map<String, Order> byId = new HashMap<>();

    public void save(Order order) {
        orders.add(order);
        byId.put(order.getId(), order);
    }

    public List<Order> findAll() {
        return orders;
    }

    public Order findById(String orderId) {
        sleep(15);
        return byId.get(orderId);
    }

    public Order findByExternalId(String customerId, String externalId) {
        sleep(25);
        for (Order order : orders) {
            if (order.getCustomerId().equals(customerId) && order.getExternalId().equals(externalId)) {
                return order;
            }
        }
        return null;
    }

    public int size() {
        return orders.size();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
