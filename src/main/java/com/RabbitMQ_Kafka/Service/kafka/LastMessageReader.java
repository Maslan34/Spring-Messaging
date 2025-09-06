package com.RabbitMQ_Kafka.Service.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class LastMessageReader {

    public Map<String, String> readLastMessage(String topicName) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "unique-key-reader");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // start from head

        Map<String, String> latestKeyValues = new HashMap<>();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            List<PartitionInfo> partitions = consumer.partitionsFor(topicName);
            List<TopicPartition> topicPartitions = new ArrayList<>();

            for (PartitionInfo partition : partitions) {
                topicPartitions.add(new TopicPartition(topicName, partition.partition()));
            }

            consumer.assign(topicPartitions);

            // Go to the beginning of each partition
            consumer.seekToBeginning(topicPartitions);

            boolean keepReading = true;

            while (keepReading) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                if (records.isEmpty()) {
                    keepReading = false;
                }

                for (ConsumerRecord<String, String> record : records) {
                    // In Kafka, records with tombstone (value=null) are considered deleted
                    if (record.value() != null) {
                        latestKeyValues.put(record.key(), record.value());
                    } else {
                        latestKeyValues.remove(record.key());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return latestKeyValues;
    }
}
