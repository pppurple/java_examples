package com.example.stream.kafka;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // configuration
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "myStream");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, "Serdes.StringSerde.class");
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, "Serdes.StringSerde.class");

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> kStream = streamsBuilder.stream("stream_topic");

        kStream.filter(((key, value) -> {
            if (value.startsWith("abc")) {
                return true;
            }
            return false;
        })).foreach(((key, value) -> {
            System.out.println("key: " + key + ", value: " + value);
        }));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);

        streams.start();

        Thread.sleep(100_000L);
    }
}
