package com.interview.legacy.domain;

public class Customer {
    private final String id;
    private final String name;
    private final String email;
    private final String region;
    private final boolean vip;
    private final int riskScore;

    public Customer(String id, String name, String email, String region, boolean vip, int riskScore) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.region = region;
        this.vip = vip;
        this.riskScore = riskScore;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRegion() {
        return region;
    }

    public boolean isVip() {
        return vip;
    }

    public int getRiskScore() {
        return riskScore;
    }
}
