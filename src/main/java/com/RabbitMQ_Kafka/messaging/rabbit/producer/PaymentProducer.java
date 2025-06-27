package com.RabbitMQ_Kafka.messaging.rabbit.producer;

import com.RabbitMQ_Kafka.Model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {

    @Value("${sample.rabbitmq.payment.exchange}")
    private String paymentExchange;

    @Value("${sample.rabbitmq.payment.routingKey}")
    private String paymentRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public PaymentProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(Order order) {
        System.out.println("Sending Order to Payment Consumer in Payment Producer\n");
        rabbitTemplate.convertAndSend(paymentExchange, paymentRoutingKey, order);
    }
}

