package com.razorpay.models;

import com.razorpay.strategy.LoadBalancingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Service {
    private String serviceId;
    private List<IPAddress> ipAddresses;
    private List<String> endPoints;
    private LoadBalancingStrategy loadBalancingStrategy;

    public Service(String serviceId, List<IPAddress> ipAddresses, List<String> endPoints, LoadBalancingStrategy loadBalancingStrategy) {
        this.serviceId = serviceId;
        this.ipAddresses = ipAddresses;
        this.endPoints = endPoints;
        this.loadBalancingStrategy = loadBalancingStrategy;
    }
}
