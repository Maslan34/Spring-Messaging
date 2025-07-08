package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Email;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailConsumer {

    @RabbitListener(queues = "${sample.rabbitmq.mail.queue}", containerFactory = "manualAck")
    public void consume(Message message, Channel channel, @Payload Email email) throws IOException {
        try {
            String payload = new String(message.getBody());
            System.out.println("Email: " + payload);

            if (email == null || email.getEmail() == null || !email.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                System.out.println("Invalid , sending to DLX!");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                return;
            }

            System.out.println("Email is OK!");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
