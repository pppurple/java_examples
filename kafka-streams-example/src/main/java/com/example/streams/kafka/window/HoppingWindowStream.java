package com.example.streams.kafka.window;

import com.example.streams.kafka.serdes.CountStoreSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;

import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class HoppingWindowStream {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "hopping_window");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> kStream = streamsBuilder.stream("hopping");

        KTable<Windowed<String>, CountStore> tumblingWindowCount = kStream
                .groupBy((key, word) -> word)
                .windowedBy(TimeWindows.of(Duration.ofMillis(5_000L)).advanceBy(Duration.ofMillis(2_000L)))
                .aggregate(CountStore::new,
                        (k, v, countStore) -> countStore.increment(v),
                        Materialized.as("hopping-counts-store").with(Serdes.String(), new CountStoreSerde())
                );

        tumblingWindowCount
                .toStream()
                .map((windowed, countStore) -> {
                    String start = windowed.window().startTime().atZone(ZoneId.systemDefault()).format(formatter);
                    String end = windowed.window().endTime().atZone(ZoneId.systemDefault()).format(formatter);
                    countStore.setStart(start);
                    countStore.setEnd(end);
                    return new KeyValue<>(start, countStore);
                })
                .to("hopping-count", Produced.with(Serdes.String(), new CountStoreSerde()));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);

        // streams.cleanUp();

        streams.start();
    }
}
