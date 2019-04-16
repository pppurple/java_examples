package com.example.streams.kafka.window;

import com.example.streams.kafka.serdes.CountStoreDeserializer;
import com.example.streams.kafka.serdes.CountStoreSerde;
import com.example.streams.kafka.serdes.CountStoreSerializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream.WindowedSerdes;

import java.time.Duration;
import java.util.Properties;

public class TumblingWindowStream {
    public static void main(String[] args) {
        // configuration
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "tumbling_window");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> kStream = streamsBuilder.stream("tumbling20");

        Duration windowSize = Duration.ofMillis(5_000L);

        Serializer<JsonNode> jsonSerializer = new JsonSerializer();
        Deserializer<JsonNode> jsonDeserializer = new JsonDeserializer();
        Serde<JsonNode> jsonSerde = Serdes.serdeFrom(
                jsonSerializer,
                jsonDeserializer
        );

        Serializer<CountStore> countStoreSerializer = new CountStoreSerializer();
        Deserializer<CountStore> countStoreDeserializer = new CountStoreDeserializer();
        Serde<CountStore> countStoreSerde = Serdes.serdeFrom(
                countStoreSerializer,
                countStoreDeserializer
        );

        KTable<Windowed<String>, CountStore> tumblingWindowCount = kStream
                .groupBy((key, word) -> word)
                .windowedBy(TimeWindows.of(windowSize).advanceBy(Duration.ofMillis(5_000L)))
                .aggregate(CountStore::new,
                        (k, v, countStore) -> {
                            System.out.println("k:" + k + ", v:" + v);
                            System.out.println("apple:" + countStore.getApple() + ","
                                    + "banana:" + countStore.getBanana() + ","
                                    + "lomon:" + countStore.getLemon() + ","
                                    + "orange:" + countStore.getOrange()
                            );
                            return countStore.increment(v);
                        },
                        Materialized.as("tumbling-counts-store20").with(Serdes.String(), new CountStoreSerde())
                );

        tumblingWindowCount
                .toStream()
/*                .map((k, v) -> {
                    long count = countMap.get(k.key());
                    countMap.put(k.key(), count + v);
                    System.out.println("start:" + k.window().startTime().atZone(ZoneId.systemDefault()));
                    System.out.println("end:" + k.window().endTime().atZone(ZoneId.systemDefault()));
                    return new KeyValue<>(k.key(), countMap);
                })*/
                .to("tumbling-count20", Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), new CountStoreSerde()));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);

        streams.start();
    }

    @NoArgsConstructor
    @Data
    public static class CountStore {
        private long apple = 0L;
        private long banana = 0L;
        private long orange = 0L;
        private long lemon = 0L;

        public CountStore increment(String fruit) {
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
        }

        public CountStore increment(CountStore that) {
            this.apple = this.apple + that.apple;
            this.banana = this.banana + that.banana;
            this.orange = this.orange + that.orange;
            this.lemon = this.lemon + that.lemon;
            return this;
        }
    }
}
