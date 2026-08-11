package com.razorpay;

import com.razorpay.contracts.ConsumerService;
import com.razorpay.contracts.MessageQueueService;

public class ConsumerServiceImpl implements ConsumerService {

    MessageQueueService messageQueue;

    public ConsumerServiceImpl(MessageQueueService messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void subscribe(String consumerId, String topic) {
        this.messageQueue.createTopic(topic, consumerId);
    }

    @Override
    public void unsubscribe(String consumerId, String topic) {
        this.messageQueue.removeConsumer(topic, consumerId);
    }

}
