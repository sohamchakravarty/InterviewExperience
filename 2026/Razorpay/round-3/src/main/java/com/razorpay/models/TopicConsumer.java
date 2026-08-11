package com.razorpay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicConsumer {
    String topicId;
    Consumer consumer;
}