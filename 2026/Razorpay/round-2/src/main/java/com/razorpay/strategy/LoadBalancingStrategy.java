package com.razorpay.strategy;

import com.razorpay.models.IPAddress;

public interface LoadBalancingStrategy {
    IPAddress getIpAddress();
}
