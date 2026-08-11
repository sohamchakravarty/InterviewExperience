package com.razorpay.contracts;

public interface MessageQueueService {
    void addProducer(String producerId);

    void createTopic(String topicName, String consumerId);

    void addMessage(String producerId, String topicName, String messageId);

    void removeConsumer(String topicName, String consumerId);
}
