package com.example.streams.kafka.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class BasicStream {
    public void start() {
        // configuration
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "basic_stream");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<Integer, String> kStream = streamsBuilder.stream("basic");

        kStream.filter((key, value) -> value.matches("^[A-Z].+"))
                .foreach(((key, value) -> System.out.println("key: " + key + ", value: " + value)));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);

        streams.start();
    }
}
