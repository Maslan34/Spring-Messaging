package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.VenueCreated;
import com.RabbitMQ_Kafka.Model.Wedding;
import com.RabbitMQ_Kafka.Service.CateringService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CateringConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final CateringService cateringService;


    @Value("${sample.rabbitmq.saga.venue.fail.exchange}")
    private String failExchange;

    @Value("${sample.rabbitmq.saga.venue.fail.routingKey}")
    private String failRoutingKey;

    public CateringConsumer(RabbitTemplate rabbitTemplate, CateringService cateringService) {
        this.rabbitTemplate = rabbitTemplate;
        this.cateringService = cateringService;
    }

    @RabbitListener(queues = "${sample.rabbitmq.saga.venue.success.queue}")
    public void successVenue(VenueCreated venueCreated) {
        System.out.println("Venue has been reserved. Proceeding to arrange catering : " + venueCreated.toString());
        cateringService.checkCatering(venueCreated);
    }

    @RabbitListener(queues = "${sample.rabbitmq.saga.catering.fail.queue}")
    public void fallBackToVenue(Wedding wedding) {
        cateringService.fallBackCatering(wedding);
        rabbitTemplate.convertAndSend(failExchange, failRoutingKey, wedding);
    }


}
