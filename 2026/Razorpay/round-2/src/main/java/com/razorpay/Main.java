package com.razorpay;

import com.razorpay.models.IPAddress;
import com.razorpay.models.Service;
import com.razorpay.strategy.ConsistentHashingStrategy;
import com.razorpay.strategy.RoundRobinStrategy;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<IPAddress> ipAddresses1 = List.of(
                new IPAddress("192.168.1.1", true),
                new IPAddress("192.168.1.2", true)
        );
        Service service1 = new Service(
                "service-1",
                ipAddresses1,
                List.of("/api/v1/users"),
                new ConsistentHashingStrategy(ipAddresses1)
        );

        List<IPAddress> ipAddresses2 = List.of(
                new IPAddress("10.0.0.1", true),
                new IPAddress("10.0.0.2", false),
                new IPAddress("10.0.0.3", true)
        );
        Service service2 = new Service(
                "service-2",
                ipAddresses2,
                List.of("/api/v1/payments"),
                new RoundRobinStrategy(ipAddresses2)
        );

        
    }
}
