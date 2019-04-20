package com.example.producer.client.kafka.producerforwindow;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serdes;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class WordProducer {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Serdes.String().serializer().getClass());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Serdes.String().serializer().getClass());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        String[] words = {"apple", "banana", "orange", "lemon"};

        IntStream.range(0, 10_000)
                .forEach(i -> {
                    String key = "key" + i;
                    Random random = new Random();
                    String word = words[random.nextInt(words.length)];
                    ProducerRecord<String, String> record = new ProducerRecord<>("hopping", key, word);
                    try {
                        // sync send
                        Future<RecordMetadata> send = producer.send(record);
                        RecordMetadata recordMetadata = send.get();
                        System.out.println("partition: " + recordMetadata.partition() +
                                ", topic: " + recordMetadata.topic() +
                                ", offset: " + recordMetadata.offset() +
                                ", key: " + key +
                                ", value: " + word);
                        Thread.sleep(1_000L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
