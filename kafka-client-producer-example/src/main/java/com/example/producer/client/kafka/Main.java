package com.example.producer.client.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
//        properties.put("zk.connect", "127.0.0.1:2181");
//        properties.put("bootstrap.servers", "127.0.0.1:9092");
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "0.0.0.0:9092");
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.2.15:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // sync send
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
//        ProducerRecord<String, String> record = new ProducerRecord<>("test", "my first send 1");
//        ProducerRecord<String, String> record = new ProducerRecord<>("mytopic", "message", "my first send 1");
        ProducerRecord<String, String> record = new ProducerRecord<>("mytopic", "my first send 1");
        try {
            Future<RecordMetadata> send = producer.send(record);
            System.out.println(send.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void syncSend() {

    }
}
