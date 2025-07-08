package com.RabbitMQ_Kafka.Service;

import com.RabbitMQ_Kafka.Model.Email;
import com.RabbitMQ_Kafka.messaging.rabbit.producer.MailProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final MailProducer mailProducer;

    public EmailService(MailProducer mailProducer) {
        this.mailProducer = mailProducer;
    }

    public ResponseEntity<String> checkMail(Email email) {
        return mailProducer.check(email);
    }
}
