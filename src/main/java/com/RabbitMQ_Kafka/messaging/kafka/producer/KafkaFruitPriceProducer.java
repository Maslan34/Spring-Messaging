package com.RabbitMQ_Kafka.messaging.kafka.producer;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaFruitPriceProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaFruitPriceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private static final String TOPIC_COMPACT = "greengrocer-compact";
    private static final String TOPIC_DELETE = "greengrocer-delete";

    public void updatePriceCompact(String fruit, String price) {
        kafkaTemplate.send(TOPIC_COMPACT, fruit, price);
        System.out.println("Sent: " + fruit + " -> " + price);
    }

    public void updatePriceDelete(String fruit, String price) {
        kafkaTemplate.send(TOPIC_DELETE, fruit, price);
        System.out.println("Sent: " + fruit + " -> " + price);
    }
}
