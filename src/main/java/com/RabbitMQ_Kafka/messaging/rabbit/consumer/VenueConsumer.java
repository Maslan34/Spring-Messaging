package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Wedding;
import com.RabbitMQ_Kafka.Service.VenueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class VenueConsumer {


    private final VenueService venueService;

    public VenueConsumer(VenueService venueService) {
        this.venueService = venueService;
    }

    @RabbitListener(queues = "${sample.rabbitmq.saga.venue.fail.queue}")
    public void fallBackVenue(Wedding wedding) {
        this.venueService.fallBackVenue(wedding);
    }

}
