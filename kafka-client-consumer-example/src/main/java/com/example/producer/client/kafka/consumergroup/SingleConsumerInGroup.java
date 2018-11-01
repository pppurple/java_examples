package com.example.producer.client.kafka.consumergroup;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Properties;

public class SingleConsumerInGroup {
    public static void main(String[] args) {
        ConsumeTask consumeTask = new ConsumeTask("myConsumer", "sin");
        new Thread(consumeTask).start();
    }
    public void consumeFromEachPartition() {
        // configuration
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "myConsumerGroup");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);


        Runnable task = new Runnable() {
            @Override
            public void run() {
                consumer.subscribe(Collections.singletonList("sync_topic"));

                try {
                    while (true) {
                        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1_000));

                        if (records.count() > 0) {
                            System.out.println("=============================");
                            System.out.println("[record size] " + records.count());
                        }
                        records.forEach(record -> {
                            System.out.println("=============================");
                            System.out.println(LocalDateTime.now());
                            System.out.println("topic: " + record.topic());
                            System.out.println("partition: " + record.partition());
                            System.out.println("key: " + record.key());
                            System.out.println("value: " + record.value());
                            System.out.println("offset: " + record.offset());
                            TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
                            OffsetAndMetadata offsetAndMetadata = consumer.committed(topicPartition);
                            if (offsetAndMetadata != null) {
                                System.out.println("partition offset: " + offsetAndMetadata.offset());
                            }
                        });

                        Thread.sleep(1_000L);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    consumer.close();
                }
            }
        };
    }
}
