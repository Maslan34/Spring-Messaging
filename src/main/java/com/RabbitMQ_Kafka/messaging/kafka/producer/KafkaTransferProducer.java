package com.RabbitMQ_Kafka.messaging.kafka.producer;

import com.RabbitMQ_Kafka.Model.OutboxEvent;
import com.RabbitMQ_Kafka.Repository.OutboxRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("kafka")
public class KafkaTransferProducer {
    private final OutboxRepository outboxRepo;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaTransferProducer(OutboxRepository outboxRepo, KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxRepo = outboxRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 5000,initialDelay = 10000) // Starting with a 10-second delay, check every 5 seconds.
    public void publishEvents() {
        List<OutboxEvent> events = outboxRepo.findByProcessedFalse();

        for (OutboxEvent event : events) {
            kafkaTemplate.send("player-transfers", event.getPayload());
            event.setProcessed(true);
            outboxRepo.save(event);
        }
    }
}
