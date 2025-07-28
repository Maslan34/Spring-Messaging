package com.RabbitMQ_Kafka.messaging.rabbit.producer;

import com.RabbitMQ_Kafka.Model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("rabbit")
@Component
public class ShippingProducer {

    @Value("${sample.rabbitmq.shipping.exchange}")
    String shippingExchange;
    @Value("${sample.rabbitmq.shipping.routingKey}")
    String shpippingRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public ShippingProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendShipping(Order order) {
        System.out.println("Sending Order to Shipping Consumer in Shipping Producer\n");
        rabbitTemplate.convertAndSend(shippingExchange, shpippingRoutingKey, order);
    }
}
