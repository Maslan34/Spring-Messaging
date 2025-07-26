package com.RabbitMQ_Kafka.Service;

import com.RabbitMQ_Kafka.Model.CateringCreated;
import com.RabbitMQ_Kafka.Model.VenueCreated;
import com.RabbitMQ_Kafka.Model.Wedding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CateringService {

    @Value("${sample.rabbitmq.saga.entertainment.success.exchange}")
    String successExchange;
    @Value("${sample.rabbitmq.saga.entertainment.success.routingKey}")
    String successRoutingKey;

    @Value("${sample.rabbitmq.saga.venue.fail.exchange}")
    String failExchange;
    @Value("${sample.rabbitmq.saga.venue.fail.routingKey}")
    String failRoutingKey;


    private final RabbitTemplate rabbitTemplate;

    public CateringService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void checkCatering(VenueCreated venueCreated) {

        if (venueCreated.getCateringSuccess()) {
            CateringCreated cateringCreated = new CateringCreated(venueCreated.getWeddingId(), "Good Catering Gold", venueCreated.getEntertainmentSuccess(), venueCreated.getInvitationSuccess());
            rabbitTemplate.convertAndSend(successExchange, successRoutingKey, cateringCreated);
        } else {
            System.out.println("Catering Failed!");
            rabbitTemplate.convertAndSend(failExchange, failRoutingKey, venueCreated);
        }

    }

    public void fallBackCatering(Wedding wedding) {
        System.out.println("Refunding the payment made for catering.");

    }


}
