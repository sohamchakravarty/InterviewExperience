package com.interview.legacy.persistence;

import com.interview.legacy.domain.Customer;
import java.util.HashMap;
import java.util.Map;

public class CustomerRepository {
    private final Map<String, Customer> customers = new HashMap<>();

    public CustomerRepository() {
        customers.put("cust-1", new Customer("cust-1", "Ada Lovelace", "ada@example.com", "US", true, 8));
        customers.put("cust-2", new Customer("cust-2", "Grace Hopper", "grace@navy.mil", "US", false, 15));
        customers.put("cust-3", new Customer("cust-3", "Linus Torvalds", "linus@example.com", "EU", false, 22));
        customers.put("cust-4", new Customer("cust-4", "Barbara Liskov", "barbara@example.com", "APAC", true, 5));
    }

    public Customer findById(String customerId) {
        sleep(90);
        Customer customer = customers.get(customerId);
        if (customer == null) {
            return new Customer(customerId, "Guest-" + customerId, customerId + "@unknown.local", "US", false, 40);
        }
        return customer;
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
