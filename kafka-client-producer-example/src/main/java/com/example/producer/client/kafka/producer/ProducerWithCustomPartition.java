package com.example.producer.client.kafka.producer;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class ProducerWithCustomPartition {
    public void syncSend() {
        // configuration
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.example.producer.client.kafka.partitioner.CustomPartitioner");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        IntStream.range(0, 100)
                .forEach(i -> {
                    String key = RandomStringUtils.randomAlphabetic(5);
                    String value = "num" + i;
                    ProducerRecord<String, String> record = new ProducerRecord<>("topicCus", key, value);
                    try {
                        // sync send
                        Future<RecordMetadata> send = producer.send(record);
                        RecordMetadata recordMetadata = send.get();
                        System.out.print("partition: " + recordMetadata.partition() + ", ");
                        System.out.print("topic: " + recordMetadata.topic() + ", ");
                        System.out.print("key: " + key + ", ");
                        System.out.println("value: " + value);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
