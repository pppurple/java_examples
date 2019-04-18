package com.example.streams.kafka.serdes;

import com.example.streams.kafka.window.CountStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class CountStoreDeserializer implements Deserializer<CountStore> {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public CountStore deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, CountStore.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
    }
}
