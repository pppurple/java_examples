package com.example.streams.kafka.window;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CountStore {
    private String name;
    private int count;
    private String start;
    private String end;

    CountStore increment(String name) {
        this.name = name;
        this.count++;
        return this;
    }
}
