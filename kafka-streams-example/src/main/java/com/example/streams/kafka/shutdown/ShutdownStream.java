package com.example.streams.kafka.shutdown;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class ShutdownStream {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "basic_stream");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<Integer, String> kStream = streamsBuilder.stream("s");

        kStream.foreach(((key, value) -> System.out.println("key: " + key + ", value: " + value)));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);

        Runtime.getRuntime().addShutdownHook(new Thread("stream-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                System.out.println("close stream.");
            }
        });

        try {
            streams.start();
        } catch (final Throwable ignore) {
            System.exit(1);
        }
    }
}
