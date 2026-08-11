package com.razorpay.contracts;

public interface ConsumerService {
    void subscribe(String consumerId, String topic);

    void unsubscribe(String consumerId, String topic);
}