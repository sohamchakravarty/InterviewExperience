package com.interview.legacy.persistence;

import java.util.HashMap;
import java.util.Map;

import com.interview.legacy.domain.Order;

public class RequestRepository {

    private final Map<String, Order> requestIdToOrder = new HashMap<>();

    public void recordRequest(String requestId, Order order) {
        if (requestId != null && !requestId.isBlank()) {
            requestIdToOrder.put(requestId, order);
        }
    }

    public Order findOrderById(String requestId) {
        if (requestId == null || requestId.isBlank()) {
            return null;
        }
        return requestIdToOrder.get(requestId);
    }

    public boolean hasRequest(String requestId) {
        if (requestId == null || requestId.isBlank()) {
            return false;
        }
        return requestIdToOrder.containsKey(requestId);
    }

    public int size() {
        return requestIdToOrder.size();
    }
}
