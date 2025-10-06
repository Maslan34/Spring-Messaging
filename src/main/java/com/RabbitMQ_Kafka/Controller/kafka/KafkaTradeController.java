package com.RabbitMQ_Kafka.Controller.kafka;

import com.RabbitMQ_Kafka.Service.kafka.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka/trade")
@Slf4j
@Profile("kafka-security")
public class KafkaTradeController {

    private final TradeService tradeService;

    public KafkaTradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/long")
    public ResponseEntity<String> executeLongTrade(
            @RequestParam String symbol,
            @RequestParam int quantity,
            @RequestParam float price,
            @RequestHeader String userId) {

        try {
            tradeService.executeLongTrade(symbol, quantity, price, userId);
            return ResponseEntity.ok("Long trade executed successfully");
        } catch (Exception e) {
            log.error("Error executing long trade", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error executing long trade");
        }
    }

    @PostMapping("/short")
    public ResponseEntity<String> executeShortTrade(
            @RequestParam String symbol,
            @RequestParam int quantity,
            @RequestParam float price,
            @RequestHeader String userId) {

        try {
            tradeService.executeShortTrade(symbol, quantity, price, userId);
            return ResponseEntity.ok("Short trade executed successfully");
        } catch (Exception e) {
            log.error("Error executing short trade", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error executing short trade");
        }
    }
}