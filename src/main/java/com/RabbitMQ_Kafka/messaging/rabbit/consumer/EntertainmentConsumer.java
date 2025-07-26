package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.CateringCreated;
import com.RabbitMQ_Kafka.Model.Wedding;
import com.RabbitMQ_Kafka.Service.EntertainmentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EntertainmentConsumer {
    private final RabbitTemplate rabbitTemplate;
    private final EntertainmentService entertainmentService;


    @Value("${sample.rabbitmq.saga.catering.fail.exchange}")
    private String failExchange;

    @Value("${sample.rabbitmq.saga.catering.fail.routingKey}")
    private String failRoutingKey;

    public EntertainmentConsumer(RabbitTemplate rabbitTemplate, EntertainmentService entertainmentService) {
        this.rabbitTemplate = rabbitTemplate;
        this.entertainmentService = entertainmentService;
    }

    @RabbitListener(queues = "${sample.rabbitmq.saga.entertainment.success.queue}")
    public void successEntertainment(CateringCreated cateringCreated) {
        System.out.println("Catering service has been arranged. Proceeding with entertainment arrangements: " + cateringCreated.toString());
        entertainmentService.checkEntertainment(cateringCreated);
    }

    @RabbitListener(queues = "${sample.rabbitmq.saga.entertainment.fail.queue}")
    public void fallBackEntertainment(Wedding wedding) {

        entertainmentService.fallBackEntertainment(wedding);
        rabbitTemplate.convertAndSend(failExchange, failRoutingKey, wedding);
    }


}




