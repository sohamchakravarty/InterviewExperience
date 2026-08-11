package com.razorpay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Consumer {
    String consumerId;

    public void process(Message message) {
        System.out.println(consumerId + "_" + message.messageId);
    }
}
