package com.RabbitMQ_Kafka.messaging.rabbit.producer;

import com.RabbitMQ_Kafka.Model.Email;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MailProducer {


    @Value("${sample.rabbitmq.mail.exchange}")
    String mailExchange;
    @Value("${sample.rabbitmq.mail.routingKey}")
    String mailRoutingKey;


    private final RabbitTemplate rabbitTemplate;

    public MailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public ResponseEntity<String> check(Email email) {


        rabbitTemplate.convertAndSend(mailExchange, mailRoutingKey, email, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });

        return ResponseEntity.ok("OK");
    }
}
