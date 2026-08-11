package com.razorpay.models;

import lombok.Getter;

@Getter
public class IPAddress {
    private String id;
    private Boolean isHealthy;

    public IPAddress(String id, Boolean isHealthy) {
        this.id = id;
        this.isHealthy = isHealthy;
    }
}
