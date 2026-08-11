package com.razorpay;

import com.razorpay.contracts.MessageQueueService;
import com.razorpay.contracts.ProducerService;

public class ProducerServiceImpl implements ProducerService {

    private MessageQueueService messageQueue;

    public ProducerServiceImpl(MessageQueueService messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void addProducer(String producerId) {
        this.messageQueue.addProducer(producerId);
    }

    @Override
    public void publishMessage(String producerId, String topic, String message) {
        this.messageQueue.addMessage(producerId, topic, message);
    }

}
