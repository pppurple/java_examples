package com.example.producer.client.kafka.consumergroup;

public class SingleConsumerInGroup {
    public static void main(String[] args) {
        ConsumeTask consumeTask = new ConsumeTask("myConsumer", "sin");
        new Thread(consumeTask).start();
    }
}
