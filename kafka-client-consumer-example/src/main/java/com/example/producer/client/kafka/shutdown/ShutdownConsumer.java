package com.example.producer.client.kafka.shutdown;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ShutdownConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "shutdown_consumer");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        final Thread mainThread = Thread.currentThread();

        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singletonList("s1"));

        Runtime.getRuntime().addShutdownHook(new Thread("shutdown-thread") {
            public void run() {
                System.out.println("start to wakeup");

                // throw WakeUpException
                consumer.wakeup();

                System.out.println("end to wakeup");
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            while (true) {
                ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(1_000));

                // print information about topic record
                records.forEach(record -> System.out.println( "topic: " + record.topic()
                        + ", partition: " + record.partition()
                        + ", key: " + record.key()
                        + ", value: " + record.value()
                ));
                Thread.sleep(1_000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (WakeupException ignore) {
            System.out.println("thrown WakeUpException");
        } finally {
            consumer.close();
            System.out.println("closed consumer");
        }
    }
}
