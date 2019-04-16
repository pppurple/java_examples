package com.example.streams.kafka.window;

import com.example.streams.kafka.serdes.CountStoreSerde;
import lombok.Data;
import lombok.NoArgsConstructor;
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

public class TumblingWindowStream {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "tumbling_window");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> kStream = streamsBuilder.stream("tumbling20");

        KTable<Windowed<String>, CountStore> tumblingWindowCount = kStream
                .groupBy((key, word) -> word)
                .windowedBy(TimeWindows.of(Duration.ofMillis(5_000L)).advanceBy(Duration.ofMillis(5_000L)))
                .aggregate(CountStore::new,
                        (k, v, countStore) -> {
                            System.out.println("k:" + k + ", v:" + v);
                            System.out.println("name: " + countStore.name + ", count: " + countStore.count);
                            return countStore.increment(v);
                        },
                        Materialized.as("tumbling-counts-store20").with(Serdes.String(), new CountStoreSerde())
                );

        tumblingWindowCount
                .toStream()
                .map((windowed, countStore) -> {
                    String start = windowed.window().startTime().atZone(ZoneId.systemDefault()).format(formatter);
                    String end = windowed.window().endTime().atZone(ZoneId.systemDefault()).format(formatter);
                    System.out.println("start: " + start);
                    System.out.println("end: " + end);
                    return new KeyValue<>(start, countStore);
                })
                .to("tumbling-count20", Produced.with(Serdes.String(), new CountStoreSerde()));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);

        // streams.cleanUp();

        streams.start();
    }

    @NoArgsConstructor
    @Data
    public static class CountStore {
        private String name;
        private int count;

        private long apple = 0L;
        private long banana = 0L;
        private long orange = 0L;
        private long lemon = 0L;

        CountStore increment(String fruit) {
            this.name = fruit;
            this.count++;
            return this;
        }

/*        public CountStore increment(String fruit) {
            switch (fruit) {
                case "apple":
                    this.apple += 1L;
                    break;
                case "banana":
                    this.banana += 1L;
                    break;
                case "orange":
                    this.orange += 1L;
                    break;
                case "lemon":
                    this.lemon += 1L;
                    break;
            }
            return this;
        }*/

        public CountStore increment(CountStore that) {
            this.apple = this.apple + that.apple;
            this.banana = this.banana + that.banana;
            this.orange = this.orange + that.orange;
            this.lemon = this.lemon + that.lemon;
            return this;
        }
    }
}
