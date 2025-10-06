package com.RabbitMQ_Kafka.messaging.kafka.producer;

import com.RabbitMQ_Kafka.Model.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@Profile("kafka-security")
public class KafkaTradeProducer {

    private static final String TRADE_TOPIC = "trade-transactions";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaTradeProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTradeTransaction(Trade transaction) {
        try {

            transaction.setTransactionId(UUID.randomUUID().toString());
            transaction.setTimestamp(LocalDateTime.now());


            CompletableFuture<SendResult<String, Object>> future =
                    kafkaTemplate.send(TRADE_TOPIC, transaction.getUserId(), transaction);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Trade transaction sent successfully: {} - {} - Offset: {}",
                            transaction.getTradeType(),
                            transaction.getSymbol(),
                            result.getRecordMetadata().offset());
                } else {
                    log.error("Failed to send trade transaction: {}",
                            transaction.getTransactionId(), ex);
                }
            });

        } catch (Exception e) {
            log.error("Error sending trade transaction to Kafka", e);
        }
    }
}