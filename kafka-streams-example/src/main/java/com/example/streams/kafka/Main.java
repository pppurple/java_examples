package com.example.streams.kafka;

import com.example.streams.kafka.stream.BasicStream;
import com.example.streams.kafka.stream.WordCount;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // basic
        BasicStream basicStream = new BasicStream();
        basicStream.start();

        // word count
        WordCount wordCount = new WordCount();
        wordCount.start();
    }
}
