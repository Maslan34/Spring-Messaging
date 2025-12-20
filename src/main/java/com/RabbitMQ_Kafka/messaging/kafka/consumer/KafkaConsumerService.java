package com.RabbitMQ_Kafka.messaging.kafka.consumer;


import com.RabbitMQ_Kafka.Model.Email;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Profile("kafka")
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "string-topic", groupId = "string-topic", containerFactory = "stringKafkaListenerFactory")
    public void listen(String message) {
        System.out.println("Consumed message: " + message);
    }

    @KafkaListener(topics = "json-topic", groupId = "json-topic", containerFactory = "genericKafkaListenerFactory")
    public void consume(Email email) {
        System.out.println("Received Email: " + email);
    }
}