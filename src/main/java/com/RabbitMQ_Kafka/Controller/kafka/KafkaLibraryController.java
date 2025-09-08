package com.RabbitMQ_Kafka.Controller.kafka;

import com.RabbitMQ_Kafka.messaging.kafka.producer.KafkaLibraryProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka/library")
public class KafkaLibraryController {


    private final KafkaLibraryProducer producerService;

    public KafkaLibraryController(KafkaLibraryProducer producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendBook(
            @RequestParam String genre,
            @RequestParam String book) {
        producerService.sendBook(genre, book);
        return ResponseEntity.ok("Book Sent!");
    }
}


