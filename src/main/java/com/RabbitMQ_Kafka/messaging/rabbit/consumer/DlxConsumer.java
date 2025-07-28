package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Profile("rabbit")
@Component
public class DlxConsumer {

    @RabbitListener(queues = "${sample.rabbitmq.dlx.mail.queue}")
    public void handleDeadMessages(Message message) {
        System.err.println("DLX Consumer - Received Email: " + new String(message.getBody()));
    }
}
