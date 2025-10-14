package com.RabbitMQ_Kafka.Controller.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka/game")
@Profile("kafka")
public class KafkaOnlineGameController {

    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final KafkaTemplate<String, String> transactionalKafkaTemplate;

    public KafkaOnlineGameController(
            @Qualifier("stringKafkaTemplate") KafkaTemplate<String, String> stringKafkaTemplate,
            @Qualifier("transactionalKafkaTemplate") KafkaTemplate<String, String> transactionalKafkaTemplate) {
        this.stringKafkaTemplate = stringKafkaTemplate;
        this.transactionalKafkaTemplate = transactionalKafkaTemplate;
    }

    @GetMapping("/atmost")
    public String sendAtMost(@RequestParam String playerId, @RequestParam String points) {
        try {
            stringKafkaTemplate.send("game-scores-atmost", playerId + ":" + points);
            return "✅ AT-MOST-ONCE: Score sent for player " + playerId + " with " + points + " points";
        } catch (Exception e) {
            return "❌ AT-MOST-ONCE: Failed to send score - message lost!";
        }
    }

    @GetMapping("/atleast")
    public String sendAtLeast(@RequestParam String playerId, @RequestParam String points) {
        try {
            stringKafkaTemplate.send("game-scores-atleast", playerId + ":" + points).get();
            return "✅ AT-LEAST-ONCE: Score sent for player " + playerId + " with " + points + " points";
        } catch (Exception e) {
            // Retry mechanism works automatically
            return "❌ AT-LEAST-ONCE: Send may be retried - " + e.getMessage();
        }
    }


    @GetMapping("/exactly")
    @Transactional("kafkaTransactionManager")
    public String sendExactly(@RequestParam String playerId, @RequestParam String points) {
        try {

            transactionalKafkaTemplate.send("game-scores-exactly", playerId + ":" + points);

            return "✅ EXACTLY-ONCE: Score sent transactionally for player " + playerId
                    + " with " + points + " points";

        } catch (Exception e) {
            // Transaction will rollback
            return "❌ EXACTLY-ONCE: Transaction failed - " + e.getMessage();
        }
    }


}