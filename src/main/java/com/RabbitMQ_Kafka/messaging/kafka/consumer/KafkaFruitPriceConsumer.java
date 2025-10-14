package com.RabbitMQ_Kafka.messaging.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class KafkaFruitPriceConsumer {

    @KafkaListener(topics = "greengrocer-compact", groupId = "compact", containerFactory = "stringKafkaListenerFactory")

    public void readPriceCompact(ConsumerRecord<String, String> record) {
        System.out.println("Fruit: " + record.key() + " Price: " + record.value());
    }

    @KafkaListener(topics = "greengrocer-delete", groupId = "delete", containerFactory = "stringKafkaListenerFactory")
    public void readPriceDelete(ConsumerRecord<String, String> record) {
        System.out.println("Fruit: " + record.key() + " Price: " + record.value());
    }

}


