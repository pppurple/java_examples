package com.example.producer.client.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.stream.IntStream;

public class AsyncSendProducer {
    public void asyncSend() {
        // configuration
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        IntStream.range(0, 10)
                .forEach(i -> {
                    ProducerRecord<String, String> record = new ProducerRecord<>("topic_1", "async_value-" + i);
                    try {
                        // async send
                        producer.send(record, (metadata, exception) -> {
                            System.out.println("=============================");
                            System.out.println(LocalDateTime.now());
                            System.out.println("callback!!");
                            System.out.println("topic: " + metadata.topic());
                            System.out.println("partition: " + metadata.partition());
                            System.out.println("offset: " + metadata.offset());
                        });

                        Thread.sleep(1_000L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
