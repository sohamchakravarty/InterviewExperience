package com.interview.legacy.support;

import com.interview.legacy.domain.Order;
import com.interview.legacy.persistence.OrderRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReconciliationScheduler {
    private final List<String> history = new ArrayList<>();
    private final Timer timer = new Timer("legacy-reconciliation");

    public void start(OrderRepository orderRepository, AuditTrail auditTrail) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<Order> orders = orderRepository.findAll();
                sleep(350);
                history.add(Instant.now() + "|snapshotSize=" + orders.size() + "|orders=" + orders);
                auditTrail.record("reconciliation-ran size=" + orders.size());
            }
        }, 5_000, 20_000);
    }

    public int historySize() {
        return history.size();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
