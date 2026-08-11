package com.interview.legacy.support;

import com.interview.legacy.domain.Order;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class ExpiringOrderCache {
    private final Map<String, CacheValue> entries = new ConcurrentHashMap<>();
    private final Timer timer = new Timer("legacy-order-cache-cleaner");

    public ExpiringOrderCache() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                entries.entrySet().removeIf(entry -> entry.getValue().expiresAt < now);
            }
        }, 30_000, 30_000);
    }

    public void put(String key, Order value) {
        entries.put(key, new CacheValue(value, System.currentTimeMillis() + 15 * 60 * 1000));
    }

    public Order get(String key) {
        CacheValue value = entries.get(key);
        if (value == null) {
            return null;
        }
        return value.order;
    }

    public int size() {
        return entries.size();
    }

    private static class CacheValue {
        private final Order order;
        private final long expiresAt;

        private CacheValue(Order order, long expiresAt) {
            this.order = order;
            this.expiresAt = expiresAt;
        }
    }
}
