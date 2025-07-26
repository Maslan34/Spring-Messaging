package com.RabbitMQ_Kafka.Service;

import com.RabbitMQ_Kafka.Model.CateringCreated;
import com.RabbitMQ_Kafka.Model.EntertainmentCreated;
import com.RabbitMQ_Kafka.Model.Wedding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EntertainmentService {

    @Value("${sample.rabbitmq.saga.invitation.success.exchange}")
    String successExchange;
    @Value("${sample.rabbitmq.saga.invitation.success.routingKey}")
    String successRoutingKey;

    @Value("${sample.rabbitmq.saga.catering.fail.exchange}")
    String failExchange;
    @Value("${sample.rabbitmq.saga.catering.fail.routingKey}")
    String failRoutingKey;


    private final RabbitTemplate rabbitTemplate;

    public EntertainmentService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void checkEntertainment(CateringCreated cateringCreated) {

        if (cateringCreated.getEntertainmentSuccess()) {
            EntertainmentCreated entertainmentCreated = new EntertainmentCreated(cateringCreated.getWeddingId(), "Entertainment Concert with Adele", cateringCreated.getInvitationSuccess());
            rabbitTemplate.convertAndSend(successExchange, successRoutingKey, entertainmentCreated);
        } else {
            System.out.println("Entertainment Failed!");
            rabbitTemplate.convertAndSend(failExchange, failRoutingKey, cateringCreated);
        }

    }

    public void fallBackEntertainment(Wedding wedding) {
        System.out.println("Refunding the payment made for entertainment services.");
    }


}
