package com.example.logger.fluent;

import org.fluentd.logger.FluentLogger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    // private static FluentLogger LOGGER = FluentLogger.getLogger("app");
    // private static FluentLogger LOGGER = FluentLogger.getLogger("app", "127.0.0.1", 24224);
    private static FluentLogger LOGGER = FluentLogger.getLogger("app", "localhost", 24224);

    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", "Andy");
        data.put("age", "21");
        data.put("country", "America");
        data.put("timestamp", new Date());

        boolean result = LOGGER.log("user", data);
        System.out.println(result);
    }
}
