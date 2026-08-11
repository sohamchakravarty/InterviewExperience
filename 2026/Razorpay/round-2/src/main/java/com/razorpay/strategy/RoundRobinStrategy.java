package com.razorpay.strategy;

import com.razorpay.models.IPAddress;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinStrategy implements LoadBalancingStrategy {
    private final List<IPAddress> ipAddresses;
    private final AtomicInteger counter = new AtomicInteger(0);

    public RoundRobinStrategy(List<IPAddress> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    @Override
    public IPAddress getIpAddress() {
        List<IPAddress> healthy = ipAddresses.stream()
                .filter(IPAddress::getIsHealthy)
                .toList();

        if (healthy.isEmpty()) {
            throw new IllegalStateException("No healthy IP addresses available");
        }

        int index = counter.getAndIncrement() % healthy.size();
        return healthy.get(index);
    }
}
