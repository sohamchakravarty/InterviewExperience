package com.example;

import lombok.Getter;

@Getter
public class Transaction {
    private String transactionId;
    private String accountId;
    private double amount;
    private String timestamp;
    private String countryCode;

    public Transaction(String transactionId, String accountId, double amount, String timestamp, String countryCode) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.countryCode = countryCode;
    }
}
