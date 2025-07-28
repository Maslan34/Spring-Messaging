package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.EntertainmentCreated;
import com.RabbitMQ_Kafka.Service.rabbit.InvitationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Profile("rabbit")
@Component
public class InvitationConsumer {


    private final InvitationService invitationService;


    public InvitationConsumer(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @RabbitListener(queues = "${sample.rabbitmq.saga.invitation.success.queue}")
    public void successVenue(EntertainmentCreated entertainmentCreated) {
        System.out.println("Entertainment arrangements completed. Sending invitations to all guests: " + entertainmentCreated.toString());
        invitationService.checkInvitation(entertainmentCreated);
    }

}
