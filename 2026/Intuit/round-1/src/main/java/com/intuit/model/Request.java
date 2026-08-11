package com.intuit.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;

@Getter
public class Request {
    String requestId;
    Tenant tenant;
    Instant createTime;

    public Request(Tenant tenant) {
        this.requestId = UUID.randomUUID().toString();
        this.tenant = tenant;
        this.createTime = Instant.now();
    }
}
