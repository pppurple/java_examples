package com.example.producer.client.kafka;

import com.example.producer.client.kafka.producer.AsyncSendProducer;
import com.example.producer.client.kafka.producer.SyncSendProducer;

public class Main {
    public static void main(String[] args) {
        // sync send
        SyncSendProducer syncSendProducer = new SyncSendProducer();
        syncSendProducer.syncSend();

        // async send
        /*
        AsyncSendProducer asyncSendProducer = new AsyncSendProducer();
        asyncSendProducer.asyncSend();
        */
    }
}
