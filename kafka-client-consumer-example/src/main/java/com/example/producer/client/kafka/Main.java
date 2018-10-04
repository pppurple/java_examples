package com.example.producer.client.kafka;

import com.example.producer.client.kafka.consumer.AutoCommitConsumer;
import com.example.producer.client.kafka.consumer.SeekToBeginningConsumer;

public class Main {
    public static void main(String[] args) {
        // consumer
        AutoCommitConsumer autoCommitConsumer = new AutoCommitConsumer();
        autoCommitConsumer.consume();

        // seek to beginning
        /*
        SeekToBeginningConsumer seekToBeginningConsumer = new SeekToBeginningConsumer();
        seekToBeginningConsumer.seekToStart();
        */
    }
}
