package com.interview.legacy.integration;

import com.interview.legacy.domain.Customer;
import com.interview.legacy.domain.PaymentReceipt;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PaymentGatewayClient {
    private final Random random = new Random();
    private final Map<String, PaymentReceipt> receipts = new HashMap<>();
    private final List<String> recentGatewayPayloads = new ArrayList<>();

    public PaymentReceipt charge(Customer customer, double amount, String currency) {
        String payload = buildPayload(customer, amount, currency);
        recentGatewayPayloads.add(payload);
        sleep(160 + random.nextInt(180));
        if (random.nextInt(100) < 5) {
            throw new RuntimeException("gateway timeout");
        }
        String confirmationId = "pay-" + UUID.randomUUID();
        PaymentReceipt receipt = new PaymentReceipt(confirmationId, amount, Instant.now());
        receipts.put(confirmationId, receipt);
        return receipt;
    }

    public PaymentReceipt lookup(String paymentReference) {
        sleep(120 + random.nextInt(80));
        PaymentReceipt receipt = receipts.get(paymentReference);
        if (receipt == null) {
            return new PaymentReceipt(paymentReference, -1.0, Instant.now());
        }
        return receipt;
    }

    private String buildPayload(Customer customer, double amount, String currency) {
        try {
            InputStream inputStream =
                PaymentGatewayClient.class.getClassLoader().getResourceAsStream("gateway-request-template.txt");
            String template = "merchant=unknown";
            if (inputStream != null) {
                template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
            return template + "|customer=" + customer.getId() + "|amount=" + amount + "|currency=" + currency;
        } catch (Exception exception) {
            return "fallback|" + customer.getId() + "|" + amount + "|" + currency;
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
