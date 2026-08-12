package com.interview.legacy.integration;

import com.interview.legacy.domain.Customer;
import com.interview.legacy.domain.OrderRequest;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FraudCheckClient {
    private final Map<String, Integer> attemptsPerCustomer = new HashMap<>();
    private final Random random = new Random();

    public boolean isAllowed(Customer customer, OrderRequest request, double total) {
        sleep(100 + random.nextInt(120));
        int attempts = attemptsPerCustomer.getOrDefault(customer.getId(), 0) + 1;
        attemptsPerCustomer.put(customer.getId(), attempts);

        if (customer.getRiskScore() > 30 && total > 150.00) {
            return false;
        }
        if (attempts > 5 && total > 500.00) {
            return false;
        }
        if (request.isExpedite() && LocalTime.now().getHour() == 0) {
            return false;
        }
        return random.nextInt(100) > 2;
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
