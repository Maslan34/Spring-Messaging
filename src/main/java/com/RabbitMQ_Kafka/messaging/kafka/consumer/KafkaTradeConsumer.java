package com.RabbitMQ_Kafka.messaging.kafka.consumer;


import com.RabbitMQ_Kafka.Model.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("kafka-security")
public class KafkaTradeConsumer {

    @KafkaListener(topics = "trade-transactions", groupId = "trade-group",containerFactory = "genericKafkaListenerFactory")
    public void consumeTradeTransaction(Trade transaction) {
        try {
            log.info("Received trade transaction: {} - {} - {}",
                    transaction.getTradeType(), transaction.getSymbol(), transaction.getQuantity());

            processTrade(transaction);

        } catch (Exception e) {
            log.error("Error processing trade transaction: {}", transaction.getTransactionId(), e);
        }
    }

    private void processTrade(Trade transaction) {
        switch (transaction.getTradeType()) {
            case LONG:
                processLongPosition(transaction);
                break;
            case SHORT:
                processShortPosition(transaction);
                break;
            default:
                log.warn("Unknown trade type: {}", transaction.getTradeType());
        }
    }

    private void processLongPosition(Trade transaction) {

        log.info("Processing LONG position for symbol: {}", transaction.getSymbol());

    }

    private void processShortPosition(Trade transaction) {

        log.info("Processing SHORT position for symbol: {}", transaction.getSymbol());

    }
}