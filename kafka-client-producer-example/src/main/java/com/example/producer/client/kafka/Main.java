package com.example.producer.client.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        IntStream.range(0, 10)
                .forEach(i -> {
                    ProducerRecord<String, String> record = new ProducerRecord<>("mytopic", "my topic-" + i);
                    try {
                        // sync send
                        Future<RecordMetadata> send = producer.send(record);
                        System.out.println(send.get());

                        Thread.sleep(1_000L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
