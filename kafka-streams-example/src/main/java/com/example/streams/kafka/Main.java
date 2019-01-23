package com.example.streams.kafka;

import com.example.streams.kafka.stream.BasicStream;
import com.example.streams.kafka.stream.Filtering;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // basic
        BasicStream basicStream = new BasicStream();
        basicStream.start();

        // filtering
        Filtering filtering = new Filtering();
        filtering.start();
    }
}
