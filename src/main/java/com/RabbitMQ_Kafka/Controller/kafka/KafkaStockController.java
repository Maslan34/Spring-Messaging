package com.RabbitMQ_Kafka.Controller.kafka;


import com.KafkaRabbitMQ.avro.StockAvro;
import com.RabbitMQ_Kafka.messaging.kafka.producer.KafkaStockProducer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/kafka/avro")
public class KafkaStockController {


    private final KafkaStockProducer producer;

    public KafkaStockController(KafkaStockProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> sendUser(@RequestBody Map<String, Object> request) {
        StockAvro stock = new StockAvro(); // Creating Empty Avro

        // Mock Data
        stock.setSymbol((String) request.get("symbol"));
        stock.setPrice(Double.valueOf(request.get("price").toString()));
        stock.setVolume(Long.valueOf(request.get("volume").toString()));
        stock.setTimestamp(Instant.ofEpochSecond(System.currentTimeMillis()));

        producer.sendUser(stock);
        return ResponseEntity.ok("Avro Sent To Kafka!");
    }
}


