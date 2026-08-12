package com.interview.legacy.persistence;

import java.util.ArrayList;
import java.util.List;

public class OrderEventStore {
    private final List<String> events = new ArrayList<>();

    public void append(String event) {
        events.add(event);
    }

    public List<String> allEvents() {
        return events;
    }

    public int size() {
        return events.size();
    }
}
