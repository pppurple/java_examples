package com.example.streams.kafka.serdes;

import com.example.streams.kafka.window.TumblingWindowStream;
import com.example.streams.kafka.window.TumblingWindowStream.CountStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CountStoreSerializer implements Serializer<CountStore> {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, CountStore data) {
        try {
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
