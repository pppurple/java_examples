package com.example.producer.client.kafka.consumergroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class TwoConsumersInEachGroup {
    public static void main(String[] args) {
        // Consumer Group1
        // Group1 has 2 consumers.
        int numConsumers1 = 2;
        ExecutorService executorService1 = Executors.newFixedThreadPool(numConsumers1);
        IntStream.rangeClosed(1, numConsumers1)
                .forEach(i -> {
                    ConsumeTask consumeTask = new ConsumeTask("group1", "consumer" + i, "gtopic");
                    executorService1.submit(consumeTask);
                });

        // Consumer Group2
        // Group2 has 4 consumers.
        int numConsumers2 = 4;
        ExecutorService executorService2 = Executors.newFixedThreadPool(numConsumers2);
        IntStream.rangeClosed(1, numConsumers2)
                .forEach(i -> {
                    ConsumeTask consumeTask = new ConsumeTask("group2", "consumer" + i, "gtopic");
                    executorService2.submit(consumeTask);
                });
    }
}
