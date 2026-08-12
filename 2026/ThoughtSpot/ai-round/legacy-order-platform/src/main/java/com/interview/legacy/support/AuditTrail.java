package com.interview.legacy.support;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AuditTrail {
    private final List<String> events = new ArrayList<>();

    public void record(String event) {
        events.add(Instant.now() + "|" + event);
    }

    public List<String> snapshot() {
        return events;
    }

    public int size() {
        return events.size();
    }
}
