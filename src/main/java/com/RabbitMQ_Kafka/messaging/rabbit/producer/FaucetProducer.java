package com.RabbitMQ_Kafka.messaging.rabbit.producer;

import com.RabbitMQ_Kafka.Model.Water;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("rabbit")
@Component
public class FaucetProducer {

    @Value("${sample.rabbitmq.loadbalancing.exchange}")
    String loadBalancingExchange;
    @Value("${sample.rabbitmq.loadbalancing.routingKey}")
    String loadBalancingRoutingKey;


    private final RabbitTemplate rabbitTemplate;


    public FaucetProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void fill(String faucetName, int liter) {
        Water message = new Water(faucetName, liter);
        rabbitTemplate.convertAndSend(
                loadBalancingExchange,
                loadBalancingRoutingKey,
                message
        );
        System.out.println(message);
    }

}
