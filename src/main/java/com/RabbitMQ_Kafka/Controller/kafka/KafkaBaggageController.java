package com.RabbitMQ_Kafka.Controller.kafka;


import com.RabbitMQ_Kafka.messaging.kafka.producer.KafkaBaggageProducer;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka/baggage")
@Profile("kafka")
public class KafkaBaggageController {
    private final KafkaBaggageProducer kafkaBaggageProducer;

    public KafkaBaggageController(KafkaBaggageProducer kafkaBaggageProducer) {
        this.kafkaBaggageProducer = kafkaBaggageProducer;
    }


    @GetMapping("/send")
    public ResponseEntity<String> sendRandomBaggage() {
        kafkaBaggageProducer.sendBaggage();
        return ResponseEntity.ok("Random baggage sent!");
    }
}
