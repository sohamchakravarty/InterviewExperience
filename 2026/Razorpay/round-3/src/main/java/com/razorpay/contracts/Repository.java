package com.razorpay.contracts;

import java.util.List;

import com.razorpay.models.Producer;
import com.razorpay.models.TopicConsumer;

public interface Repository {
    List<Producer> getAllProducers();

    void addProducer(String producerId);

    void addConsumer(String topic, String consumerId);

    void removeConsumer(String topic, String consumerId);

    List<TopicConsumer> getConsumers(String topic);
}
