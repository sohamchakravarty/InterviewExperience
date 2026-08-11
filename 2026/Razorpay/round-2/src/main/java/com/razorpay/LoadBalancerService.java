package com.razorpay;

import com.razorpay.models.Service;
import com.razorpay.strategy.LoadBalancingStrategy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoadBalancerService implements LoadBalancer {
    private final List<Service> services;
    private final Map<String, LoadBalancingStrategy> endpointStrategyMap;

    public LoadBalancerService() {
        this.services = new CopyOnWriteArrayList<>();
        this.endpointStrategyMap = new ConcurrentHashMap<>();
    }

    @Override
    public String routeToIP(String endpoint) {
        LoadBalancingStrategy strategy = endpointStrategyMap.get(endpoint);
        if (strategy == null) {
            throw new IllegalArgumentException("No service registered for endpoint: " + endpoint);
        }
        return strategy.getIpAddress().getId();
    }

    @Override
    public void addService(Service service) {
        services.add(service);
        for (String endpoint : service.getEndPoints()) {
            endpointStrategyMap.put(endpoint, service.getLoadBalancingStrategy());
        }
    }
}