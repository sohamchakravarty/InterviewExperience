package com.razorpay;

import com.razorpay.contracts.ConsumerService;
import com.razorpay.contracts.MessageQueueService;
import com.razorpay.contracts.ProducerService;
import com.razorpay.contracts.Repository;

public class Main {
    public static void main(String[] args) {
        // System.out.println("Razorpay application started.");

        Repository repository = new RepositoryImpl();
        MessageQueueService messageQueue = new MessageQueueServiceImpl(repository);
        ProducerService producerService = new ProducerServiceImpl(messageQueue);
        ConsumerService consumerService = new ConsumerServiceImpl(messageQueue);

        producerService.addProducer("producer-1");
        producerService.addProducer("producer-2");

        consumerService.subscribe("consumer-1", "topic-1");
        consumerService.subscribe("consumer-2", "topic-2");
        consumerService.subscribe("consumer-2", "topic-1");
        
        producerService.publishMessage("producer-1", "topic-1", "message-1");
        producerService.publishMessage("producer-1", "topic-2", "message-2");
        producerService.publishMessage("producer-1", "topic-1", "message-2");
        producerService.publishMessage("producer-1", "topic-1", "message-5");
    }
}
