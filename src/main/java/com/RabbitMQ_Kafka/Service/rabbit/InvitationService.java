package com.RabbitMQ_Kafka.Service.rabbit;

import com.RabbitMQ_Kafka.Model.EntertainmentCreated;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("rabbit")
@Service
public class InvitationService {


    @Value("${sample.rabbitmq.saga.entertainment.fail.exchange}")
    String failExchange;
    @Value("${sample.rabbitmq.saga.entertainment.fail.routingKey}")
    String failRoutingKey;


    private final RabbitTemplate rabbitTemplate;

    public InvitationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void checkInvitation(EntertainmentCreated entertainmentCreated) {

        if (entertainmentCreated.isInvitationSuccess()) {
            System.out.println("All processes have been completed.");
        } else {
            System.out.println("Invitation Failed!");
            rabbitTemplate.convertAndSend(failExchange, failRoutingKey, entertainmentCreated);
        }

    }
}
