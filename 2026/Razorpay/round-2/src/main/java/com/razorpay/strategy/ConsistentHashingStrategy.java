package com.razorpay.strategy;

import com.razorpay.models.IPAddress;

import java.util.List;
import java.util.TreeMap;

public class ConsistentHashingStrategy implements LoadBalancingStrategy {
    private static final int VIRTUAL_NODES = 100;
    private final TreeMap<Integer, IPAddress> ring = new TreeMap<>();
    private String requestKey = "";

    public ConsistentHashingStrategy(List<IPAddress> ipAddresses) {
        for (IPAddress ip : ipAddresses) {
            if (ip.getIsHealthy()) {
                for (int i = 0; i < VIRTUAL_NODES; i++) {
                    ring.put(hash(ip.getId() + "#" + i), ip);
                }
            }
        }
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    @Override
    public IPAddress getIpAddress() {
        if (ring.isEmpty()) {
            throw new IllegalStateException("No healthy IP addresses available");
        }

        int keyHash = hash(requestKey);
        // find the first node at or after the key's position on the ring
        var entry = ring.ceilingEntry(keyHash);
        if (entry == null) {
            entry = ring.firstEntry();
        }
        return entry.getValue();
    }

    private int hash(String key) {
        return key.hashCode();
    }
}
