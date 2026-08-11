package com.razorpay;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import com.razorpay.contracts.MessageQueueService;
import com.razorpay.contracts.Repository;
import com.razorpay.models.Message;
import com.razorpay.models.TopicConsumer;

public class MessageQueueServiceImpl implements MessageQueueService {

    private Repository repository;
    private Map<String, PriorityQueue<Message>> topicQueueMap;

    public MessageQueueServiceImpl(Repository repository) {
        this.repository = repository;
        this.topicQueueMap = new ConcurrentHashMap<>();
    }

    @Override
    public void addProducer(String producerId) {
        this.repository.addProducer(producerId);
    }

    @Override
    public void createTopic(String topicName, String consumerId) {
        this.repository.addConsumer(topicName, consumerId);
    }

    @Override
    public void addMessage(String producerId, String topicName, String messageId) {
        // do verification if correct producer
        if (!repository.getAllProducers().stream().anyMatch(p -> p.getProducerId().equals(producerId))) {
            throw new IllegalArgumentException("Invalid producer request");
        }

        Message message = new Message(messageId, topicName);
        
        PriorityQueue<Message> queue = topicQueueMap.get(topicName);
        if (queue == null) {
            queue = new PriorityQueue<>(Comparator.comparing(Message::getTimeStamp));
            topicQueueMap.put(topicName, queue);
        }

        queue.add(message);
        processMessage(topicName);
    }

    private void processMessage(String topicName) {
        List<TopicConsumer> topicConsumers = this.repository.getConsumers(topicName);

        Message message = this.topicQueueMap.get(topicName).peek();

        for(TopicConsumer consumer : topicConsumers) {
            consumer.getConsumer().process(message);
        }

        this.topicQueueMap.get(topicName).poll();
    }

    @Override
    public void removeConsumer(String topicName, String consumerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeTopic'");
    }
}
