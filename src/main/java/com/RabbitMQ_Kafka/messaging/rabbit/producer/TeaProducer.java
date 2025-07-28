package com.RabbitMQ_Kafka.messaging.rabbit.producer;

import com.RabbitMQ_Kafka.Model.Order;
import com.RabbitMQ_Kafka.Model.Tea;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("rabbit")
@Component
public class TeaProducer {

    @Value("${sample.rabbitmq.tea.exchange}")
    String teaExchange;
    @Value("${sample.rabbitmq.tea.routingKey}")
    String teaRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public TeaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void brewTea(Tea tea) {
        rabbitTemplate.convertAndSend(teaExchange, teaRoutingKey, tea, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
    }
}
