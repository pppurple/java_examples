package com.example.producer.client.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "broker1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
    }
}
