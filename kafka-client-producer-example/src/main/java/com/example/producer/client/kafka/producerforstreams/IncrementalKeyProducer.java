package com.example.producer.client.kafka.producerforstreams;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class IncrementalKeyProducer {
    public void syncSend() {
        // configuration
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(properties);

        IntStream.range(0, 100)
                .forEach(i -> {
                    String value = RandomStringUtils.randomAlphabetic(5);
                    ProducerRecord<Integer, String> record = new ProducerRecord<>("s1", i,  value);
                    try {
                        // sync send
                        Future<RecordMetadata> send = producer.send(record);
                        RecordMetadata recordMetadata = send.get();
                        System.out.println("partition: " + recordMetadata.partition() +
                                ", topic: " + recordMetadata.topic() +
                                ", offset: " + recordMetadata.offset() +
                                ", key: " + i +
                                ", value: " + value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
