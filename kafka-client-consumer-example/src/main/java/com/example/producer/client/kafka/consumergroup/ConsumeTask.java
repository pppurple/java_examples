package com.example.producer.client.kafka.consumergroup;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumeTask implements Runnable {
    private final KafkaConsumer<String, String> consumer;
    private final String consumerGroupName;
    private final String consumerName;
    private final String topic;

    public ConsumeTask(String consumerName, String topic) {
        this("myConsumerGroup", consumerName, topic);
    }

    public ConsumeTask(String consumerGroupName, String consumerName, String topic) {
        this.consumerGroupName = consumerGroupName;
        this.consumerName = consumerName;
        this.topic = topic;

        // configuration
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupName);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(properties);
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singletonList(topic));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1_000));

                // print information about topic record
                records.forEach(record -> {
                    System.out.println("group: " + consumerGroupName
                            + ", consumer: " + consumerName
                            + ", partition: " + record.partition()
                            + ", topic: " + record.topic()
                            + ", key: " + record.key()
                            + ", value: " + record.value()
                    );
                });
            }
        } finally {
            consumer.close();
        }
    }
}
