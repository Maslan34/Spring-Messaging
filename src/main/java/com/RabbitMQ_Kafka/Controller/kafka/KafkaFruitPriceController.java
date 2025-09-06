package com.RabbitMQ_Kafka.Controller.kafka;


import com.RabbitMQ_Kafka.Service.kafka.LastMessageReader;
import com.RabbitMQ_Kafka.messaging.kafka.producer.KafkaFruitPriceProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/kafka/greengrocer")
public class KafkaFruitPriceController {

    private KafkaFruitPriceProducer kafkaFruitPriceProducer;
    private final LastMessageReader lastMessageReader;

    public KafkaFruitPriceController(LastMessageReader lastMessageReader, KafkaFruitPriceProducer kafkaFruitPriceProducer) {
        this.kafkaFruitPriceProducer = kafkaFruitPriceProducer;
        this.lastMessageReader = lastMessageReader;
    }

    @GetMapping("/compact")
    public ResponseEntity<String> updatePriceCompact(
            @RequestParam String fruit,
            @RequestParam String price) {

        kafkaFruitPriceProducer.updatePriceCompact(fruit, price);
        return ResponseEntity.ok("Price Updated!");
    }

    @GetMapping("/delete")
    public ResponseEntity<String> updatePriceDelete(
            @RequestParam String fruit,
            @RequestParam String price) {

        kafkaFruitPriceProducer.updatePriceDelete(fruit, price);
        return ResponseEntity.ok("Price Updated!");
    }

    @GetMapping("/last")
    public Map<String, String> getLastMessage(
            @RequestParam String topicName
    ) {
        return lastMessageReader.readLastMessage(topicName);
    }
}
