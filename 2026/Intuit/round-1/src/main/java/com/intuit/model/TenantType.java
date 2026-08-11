package com.intuit.model;

import lombok.Getter;

public enum TenantType {
    ENTERPRISE(0),
    PREMIUM(1),
    FREE(2)
    ;

    @Getter
    private int priority;
    TenantType(int priority) {
        this.priority = priority;
    }
}
