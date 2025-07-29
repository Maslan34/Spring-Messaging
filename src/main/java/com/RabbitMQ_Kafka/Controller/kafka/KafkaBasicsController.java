package com.RabbitMQ_Kafka.Controller.kafka;

import com.RabbitMQ_Kafka.Model.Email;
import com.RabbitMQ_Kafka.messaging.kafka.producer.KafkaProducerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@Profile("kafka")
@RestController
@RequestMapping("/api/kafka")
public class KafkaBasicsController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaBasicsController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/json")
    public String publishMessage(@RequestBody Email email) {
        kafkaProducerService.sendEmail(email);
        return "Email Sent!";
    }

    @PostMapping("/string")
    public void send(@RequestParam String msg) {
        kafkaProducerService.sendMessage(msg);
    }
}
