package com.RabbitMQ_Kafka.Service;

import com.RabbitMQ_Kafka.Model.VenueCreated;
import com.RabbitMQ_Kafka.Model.Wedding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VenueService {

    @Value("${sample.rabbitmq.saga.venue.success.exchange}")
    String successExchange;
    @Value("${sample.rabbitmq.saga.venue.success.routingKey}")
    String successRoutingKey;


    private final RabbitTemplate rabbitTemplate;

    public VenueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void startWedding(Wedding wedding) {
        //Simple Mock
        String weddingId = Integer.toString((int) (Math.random() * 100));
        wedding.setId(weddingId);
        System.out.println(wedding);
        if (wedding.getVenueSuccess()) {
            VenueCreated event = new VenueCreated(weddingId, "Salon Gold", LocalDate.now().plusDays(60),
                    wedding.getCateringSuccess(), wedding.getEntertainmentSuccess(),
                    wedding.getInvitationSuccess());
            rabbitTemplate.convertAndSend(successExchange, successRoutingKey, event);
        } else
            cancelVenue(weddingId);

    }

    public void cancelVenue(String weddingId) {
        System.out.println("Venue cancelled. Not Started Wedding Process: " + weddingId);
    }

    public void fallBackVenue(Wedding wedding) {
        System.out.println("Refunding the deposit paid for the venue.");
        System.out.println("The wedding process has been cancelled.");
    }

}
