package com.example.producer.client.kafka;

import com.example.producer.client.kafka.consumer.ManualSyncCommitConsumer;

public class Main {
    public static void main(String[] args) {
        // consumer
/*        AutoCommitConsumer autoCommitConsumer = new AutoCommitConsumer();
        autoCommitConsumer.consume();*/

        ManualSyncCommitConsumer manualSyncCommitConsumer = new ManualSyncCommitConsumer();
        manualSyncCommitConsumer.consume();

        // seek to beginning
        /*
        SeekToBeginningConsumer seekToBeginningConsumer = new SeekToBeginningConsumer();
        seekToBeginningConsumer.seekToStart();
        */
    }
}
