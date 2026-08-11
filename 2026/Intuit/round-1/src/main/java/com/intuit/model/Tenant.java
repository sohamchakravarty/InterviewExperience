package com.intuit.model;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Tenant {
    String tenantId;
    TenantType tenantType;

    public Tenant(TenantType tenantType) {
        this.tenantId = UUID.randomUUID().toString();
        this.tenantType = tenantType;
    }
}
