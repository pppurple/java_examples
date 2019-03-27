package com.example.producer.client.kafka.producerforstreams;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serdes;

import java.util.Properties;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class TextProducer {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Serdes.String().serializer().getClass());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Serdes.String().serializer().getClass());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        String[] words = {
                "The Beatles were an English rock band formed in Liverpool in 1960.",
                "Led by primary songwriters Lennon and McCartney",
                "the Beatles built their reputation playing clubs in Liverpool and Hamburg over a three-year period from 1960, with Stuart Sutcliffe initially serving as bass player. The core trio of Lennon, McCartney and Harrison, together since 1958, went through a succession of drummers, including Pete Best, before asking Starr to join them in 1962.",
                "By early 1964, the Beatles were international stars, leading the British Invasion of the United States pop market and breaking numerous sales records.",
                "The Beatles are the best-selling band in history, with estimated sales of over 800 million records worldwide."
        };

        IntStream.range(0, words.length)
                .forEach(i -> {
                    String key = "key" + i;
                    String word = words[i];
                    ProducerRecord<String, String> record = new ProducerRecord<>("sen12", key, word);
                    try {
                        // sync send
                        Future<RecordMetadata> send = producer.send(record);
                        RecordMetadata recordMetadata = send.get();
                        System.out.println("partition: " + recordMetadata.partition() +
                                ", topic: " + recordMetadata.topic() +
                                ", offset: " + recordMetadata.offset() +
                                ", key: " + key +
                                ", value: " + word);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
