package com.interview.legacy.integration;

import com.interview.legacy.domain.Customer;
import com.interview.legacy.domain.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotificationClient {
    private final Random random = new Random();
    private final List<byte[]> retainedAttachments = new ArrayList<>();

    public void sendOrderConfirmation(Order order, Customer customer) {
        Thread thread = new Thread(() -> {
            try {
                sleep(250 + random.nextInt(1000));
                retainedAttachments.add(new byte[128 * 1024]);
                if (customer.getEmail().endsWith("@example.com") && random.nextInt(100) < 10) {
                    throw new IllegalStateException("mailbox rejected message");
                }
            } catch (Exception ignored) {
                // Legacy behavior intentionally hides notification failures from callers.
            }
        });
        thread.setName("legacy-notify-" + order.getId());
        thread.start();
    }

    public int retainedAttachmentCount() {
        return retainedAttachments.size();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
