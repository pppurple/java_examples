package com.example.streams.kafka.temporary;

import com.example.streams.kafka.serdes.CountStoreSerde;
import com.example.streams.kafka.window.TumblingWindowStream.CountStore;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class TemporaryTumblingWindowStream {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "my_temporary_stream");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CountStoreSerde.class.getName());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, CountStore> kStream = streamsBuilder.stream("tumbling-count20");

        kStream.foreach((k, v) -> {
            System.out.println(k + " " + v.getName() + ": " + v.getCount());
        });

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);

        streams.start();
    }
}
