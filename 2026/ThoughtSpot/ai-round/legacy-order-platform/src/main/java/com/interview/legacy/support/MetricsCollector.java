package com.interview.legacy.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class MetricsCollector {
    private final Map<String, List<Long>> latencies = new ConcurrentHashMap<>();
    private final Timer timer = new Timer("legacy-metrics-flusher");

    public MetricsCollector() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Map.Entry<String, List<Long>> entry : latencies.entrySet()) {
                    long total = 0;
                    for (Long sample : entry.getValue()) {
                        total += sample;
                    }
                    long average = entry.getValue().isEmpty() ? 0 : total / entry.getValue().size();
                    System.out.println("metric name=" + entry.getKey() + " count=" + entry.getValue().size() + " avg=" + average);
                }
            }
        }, 10_000, 10_000);
    }

    public void record(String metricName, long latencyMillis) {
        latencies.computeIfAbsent(metricName, ignored -> new ArrayList<>()).add(latencyMillis);
    }

    public int sampleCount(String metricName) {
        List<Long> samples = latencies.get(metricName);
        return samples == null ? 0 : samples.size();
    }
}
