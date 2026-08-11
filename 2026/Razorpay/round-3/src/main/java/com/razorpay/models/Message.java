package com.razorpay.models;

import java.time.Instant;

import lombok.Getter;

@Getter
public class Message {
    String messageId;
    String topic;
    Instant timeStamp;

    public Message(String messageId, String topic) {
        this.messageId = messageId;
        this.topic = topic;
        this.timeStamp = Instant.now();
    }
}
