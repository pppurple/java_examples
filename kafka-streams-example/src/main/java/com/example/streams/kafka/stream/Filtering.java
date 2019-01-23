package com.example.streams.kafka.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class Filtering {
    public void start() {
        // configuration
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "myStream");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> kStream = streamsBuilder.stream("filter");

        kStream.filter(((key, value) -> {
            String lastChar = key.substring(key.length() - 1);
            int number = Integer.parseInt(lastChar);
            if (number % 2 == 0) {
                return true;
            }
            return false;
        })).foreach(((key, value) -> {
            System.out.println("key: " + key + ", value: " + value);
        }));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);

        streams.start();
    }
}
