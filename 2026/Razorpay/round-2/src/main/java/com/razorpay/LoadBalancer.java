package com.razorpay;

import com.razorpay.models.Service;

public interface LoadBalancer {
    String routeToIP(String endpoint);

    void addService(Service service);
}
