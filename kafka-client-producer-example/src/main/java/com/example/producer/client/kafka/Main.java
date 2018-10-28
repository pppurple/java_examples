package com.example.producer.client.kafka;

import com.example.producer.client.kafka.producer.ProducerWithDefaultPartition;

public class Main {
    public static void main(String[] args) {
        // sync send
        /*
        SyncSendProducer syncSendProducer = new SyncSendProducer();
        syncSendProducer.syncSend();
        */

        // async send
        /*
        AsyncSendProducer asyncSendProducer = new AsyncSendProducer();
        asyncSendProducer.asyncSend();
        */

        // sync with partition
        ProducerWithDefaultPartition producerWithDefaultPartition = new ProducerWithDefaultPartition();
        producerWithDefaultPartition.syncSend();
    }
}
