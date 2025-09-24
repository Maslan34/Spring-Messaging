package com.RabbitMQ_Kafka.messaging.kafka.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaFederationConsumer {

    @KafkaListener(topics = "player-transfers", groupId = "federation-service",containerFactory = "stringKafkaListenerFactory")
    public void consume(String message) {
        System.out.println("--> A new transfer has been reported to the federation: " + message);

        // Here, it can be written to the federation DB.
    }
}