package com.example.logger.fluent;

import org.fluentd.logger.FluentLogger;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static FluentLogger LOGGER = FluentLogger.getLogger("app", "localhost", 24224);

    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", "Andy");
        data.put("age", "21");
        data.put("country", "America");

        LOGGER.log("user", data);
    }
}
