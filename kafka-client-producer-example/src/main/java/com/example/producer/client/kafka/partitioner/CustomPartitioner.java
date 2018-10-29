package com.example.producer.client.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;

import java.util.List;
import java.util.Map;

public class CustomPartitioner implements Partitioner {
    @Override
    public void configure(Map<String, ?> configs) {
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value,
                         byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int numPartitions = partitionInfos.size();

        if (keyBytes == null || !(key instanceof String)) {
            throw new InvalidRecordException("Unexpected key.");
        }

        String strKey = (String) key;

        // put to partition-0 if key starts with LowerCase
        if (strKey.matches("^[a-z].*")) {
            return numPartitions - 2;
        }
        // put to partition-1 if key starts with UpperCase
        if (strKey.matches("^[A-Z].*")) {
            return numPartitions - 1;
        }

        throw new InvalidRecordException("Unexpected key.");
    }

    @Override
    public void close() {
        System.out.println("close!!");
    }
}
