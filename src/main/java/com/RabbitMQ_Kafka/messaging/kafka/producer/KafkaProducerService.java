package com.RabbitMQ_Kafka.messaging.kafka.producer;

import com.RabbitMQ_Kafka.Model.Email;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Profile("kafka")
@Service
public class KafkaProducerService {

    private static final String STR_TOPIC = "string-topic";
    private static final String JSON_TOPIC = "json-topic";


    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplateEmail;

    public KafkaProducerService(
            @Qualifier("stringKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
            @Qualifier("kafkaTemplateGeneric") KafkaTemplate<String, Object> kafkaTemplateEmail) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateEmail = kafkaTemplateEmail;
    }


    public void sendMessage(String message) {
        System.out.println("Producing message: " + message);
        kafkaTemplate.send(STR_TOPIC, message);
    }


    public void sendEmail(Email email) {
        kafkaTemplateEmail.send(JSON_TOPIC, email);
    }
}
