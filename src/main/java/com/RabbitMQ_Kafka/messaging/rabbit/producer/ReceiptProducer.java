package com.RabbitMQ_Kafka.messaging.rabbit.producer;

import com.RabbitMQ_Kafka.Model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("rabbit")
@Component
public class ReceiptProducer {

    @Value("${sample.rabbitmq.receipt.exchange}")
    String receiptExchange;
    @Value("${sample.rabbitmq.receipt.routingKey}")
    String receiptRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public ReceiptProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendReceipt(Order order) {
        System.out.println("Sending Order to Receipt Consumer in Receipt Producer\n");
        rabbitTemplate.convertAndSend(receiptExchange, receiptRoutingKey, order);
    }
}
