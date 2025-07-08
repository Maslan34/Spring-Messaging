package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DlxConsumer {

    @RabbitListener(queues = "${sample.rabbitmq.dlx.mail.queue}")
    public void handleDeadMessages(Message message) {
        System.err.println("DLX Consumer - Received Email: " + new String(message.getBody()));
    }
}
