package com.RabbitMQ_Kafka.messaging.kafka.producer;


import com.RabbitMQ_Kafka.Model.Ticket;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class KafkaTicketProducer {


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaTicketProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTicketPurchase(Ticket ticketEvent) {
        kafkaTemplate.send("ticket-purchase-requests", ticketEvent.getUserId(), ticketEvent);
        System.out.println("Ticket request sent: " + ticketEvent);
    }
}


