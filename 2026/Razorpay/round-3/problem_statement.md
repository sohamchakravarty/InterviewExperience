Design an efficient in-memory queueing system.
It should support multiple producer and consumers and multiple topics. It should be thread safe and allow concurrent operations.

Functionalities

- producers
    - create/remove
    - publish a message to a topic
- MessageQueue
    - create topic(topic name, consumerId)
    - add the receiving message to a queue
        - notify the consumer of the topic
- consumers
    - subscribe to a topic
    - unsubscribe to a topic
///    - fetch the message from the MessageQueue


out-of-scope
- consumer grps.

Other req.
- queues thread safety
- concurrency of queuing and polling


---

Producer {
    String producerId;
}

Topic {
    String topicId;
    String consumerId;
}

Consumer {
    String consumerId;
}

Message {
    String messageId;
    String topic;
}

MessageQueue {
    Queue<Message> queue;
}
