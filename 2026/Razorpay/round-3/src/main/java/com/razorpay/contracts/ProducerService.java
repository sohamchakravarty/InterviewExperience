package com.razorpay.contracts;

public interface ProducerService {
    void addProducer(String producerId);

    void publishMessage(String producerId, String topic, String message);
}
