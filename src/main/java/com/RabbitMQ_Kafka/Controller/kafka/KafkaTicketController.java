package com.RabbitMQ_Kafka.Controller.kafka;

import com.RabbitMQ_Kafka.Model.Ticket;
import com.RabbitMQ_Kafka.messaging.kafka.producer.KafkaTicketProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaTicketController {

    private final KafkaTicketProducer kafkaTicketProducer;

    public KafkaTicketController(KafkaTicketProducer kafkaTicketProducer) {
        this.kafkaTicketProducer = kafkaTicketProducer;
    }

    @PostMapping("/ticket")
    public String purchaseTicket(@RequestBody Ticket ticketEvent) {
        ticketEvent.setAttempt(1); // First Attempt
        kafkaTicketProducer.sendTicketPurchase(ticketEvent);
        return "Ticket purchase request sent for user: " + ticketEvent.getUserId();
    }
}
