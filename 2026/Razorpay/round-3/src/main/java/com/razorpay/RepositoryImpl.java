package com.razorpay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.razorpay.contracts.Repository;
import com.razorpay.models.Consumer;
import com.razorpay.models.Producer;
import com.razorpay.models.TopicConsumer;

public class RepositoryImpl implements Repository {

    List<Producer> producers;
    List<TopicConsumer> consumers;

    public RepositoryImpl() {
        this.producers = Collections.synchronizedList(new ArrayList<>());
        this.consumers = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void addProducer(String producerId) {
        this.producers.add(new Producer(producerId));
    }

    @Override
    public void addConsumer(String topic, String consumerId) {
        this.consumers.add(new TopicConsumer(topic, new Consumer(consumerId)));
    }

    @Override
    public List<TopicConsumer> getConsumers(String topic) {
        return this.consumers.stream()
                    .filter(c -> c.getTopicId().equals(topic))
                    .toList();
    }

    @Override
    public List<Producer> getAllProducers() {
        return this.producers;
    }

    @Override
    public void removeConsumer(String topic, String consumerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeConsumer'");
    }

}
