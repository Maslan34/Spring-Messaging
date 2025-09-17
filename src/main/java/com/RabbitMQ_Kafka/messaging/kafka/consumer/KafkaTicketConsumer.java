package com.RabbitMQ_Kafka.messaging.kafka.consumer;


import com.RabbitMQ_Kafka.Model.Ticket;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KafkaTicketConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaTicketConsumer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "ticket-purchase-requests", groupId = "ticket-service",containerFactory = "genericKafkaListenerFactory")
    public void consume(Ticket ticketEvent) {
        try {
            System.out.println("-> Processing ticket: " + ticketEvent);

            // Simulation: Throw an error with a 60% probability
            if (new Random().nextInt(100) < 60) {
                throw new RuntimeException("Payment service failed!");
            }

            System.out.println("-> Ticket purchased successfully for seat " + ticketEvent.getSeatNo());

        } catch (Exception e) {
            int attempt = ticketEvent.getAttempt() + 1;

            if (attempt <= 3) { // retry max 3 times
                ticketEvent.setAttempt(attempt);
                System.out.println("--> Retry attempt " + attempt + " for " + ticketEvent.getSeatNo());
                kafkaTemplate.send("ticket-purchase-retry", ticketEvent.getUserId(), ticketEvent);
            } else {
                System.out.println("--> Event moved to Dead Letter Topic(DLT): " + ticketEvent);
                System.out.println("You can observe this event on Kafka-UI");
                kafkaTemplate.send("ticket-purchase-dlt", ticketEvent.getUserId(), ticketEvent);
            }
        }
    }

    @KafkaListener(topics = "ticket-purchase-retry", groupId = "ticket-service-retry")
    public void retry(Ticket ticketEvent) throws InterruptedException {
        // a small delay (backoff) before retry
        Thread.sleep(2000);
        consume(ticketEvent);
    }
}
