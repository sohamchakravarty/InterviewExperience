package com.interview.legacy.domain;

import java.time.Instant;

public class PaymentReceipt {
    private final String confirmationId;
    private final double amount;
    private final Instant chargedAt;

    public PaymentReceipt(String confirmationId, double amount, Instant chargedAt) {
        this.confirmationId = confirmationId;
        this.amount = amount;
        this.chargedAt = chargedAt;
    }

    public String getConfirmationId() {
        return confirmationId;
    }

    public double getAmount() {
        return amount;
    }

    public Instant getChargedAt() {
        return chargedAt;
    }
}
