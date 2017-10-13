package com.example.jedis;

import redis.clients.jedis.Jedis;

public class JedisExampleMain {
    public static void main( String[] args ) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println("value: " + value);
    }
}
