package com.example.producer.client.kafka;

import com.example.producer.client.kafka.consumer.AutoCommitConsumer;
import com.example.producer.client.kafka.consumer.ConsumerWithPartition;
import com.example.producer.client.kafka.consumer.ManualSyncCommitConsumer;

public class Main {
    public static void main(String[] args) {
        /*
        // auto commit
        AutoCommitConsumer autoCommitConsumer = new AutoCommitConsumer();
        autoCommitConsumer.consume();
        */

        /*
        // manual commit
        ManualSyncCommitConsumer manualSyncCommitConsumer = new ManualSyncCommitConsumer();
        manualSyncCommitConsumer.consume();
        */

        ConsumerWithPartition consumerWithPartition = new ConsumerWithPartition();
        consumerWithPartition.consumeFromEachPartition();
    }
}
