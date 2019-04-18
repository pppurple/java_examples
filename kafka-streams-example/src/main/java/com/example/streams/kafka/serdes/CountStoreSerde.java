package com.example.streams.kafka.serdes;

import com.example.streams.kafka.window.TumblingWindowStream.CountStore;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CountStoreSerde implements Serde<CountStore> {
    final private Serializer<CountStore> serializer;
    final private Deserializer<CountStore> deserializer;

    public CountStoreSerde() {
        this.serializer = new CountStoreSerializer();
        this.deserializer = new CountStoreDeserializer();
    }

    public CountStoreSerde(Serializer<CountStore> serializer, Deserializer<CountStore> deserializer) {
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        serializer.configure(configs, isKey);
        deserializer.configure(configs, isKey);
    }

    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    @Override
    public Serializer<CountStore> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<CountStore> deserializer() {
        return deserializer;
    }
}

