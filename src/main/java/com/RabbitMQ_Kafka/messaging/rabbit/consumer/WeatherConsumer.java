package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Profile("rabbit")
@Component
public class WeatherConsumer {

    @RabbitListener(queues = "${sample.rabbitmq.ege.storm.all.queue}")
    public void handleEgeStormALl(byte[] msg) {
        String message = new String(msg, StandardCharsets.UTF_8);
        System.out.println("handleEgeStormALl: " + message);
    }

    @RabbitListener(queues = "${sample.rabbitmq.ege.any.queue}")
    public void handleEgeAny(byte[] msg) {
        String message = new String(msg, StandardCharsets.UTF_8);
        System.out.println("handleEgeAny: " + message);

    }

    @RabbitListener(queues = "${sample.rabbitmq.blacksea.storm.all.queue}")
    public void handleBlackseaStormAll(byte[] msg) {
        String message = new String(msg, StandardCharsets.UTF_8);
        System.out.println("handleBlackSeaStormAll: " + message);

    }

    @RabbitListener(queues = "${sample.rabbitmq.blacksea.any.queue}")
    public void handleBlackseaAny(byte[] msg) {
        String message = new String(msg, StandardCharsets.UTF_8);
        System.out.println("handleBlackSeaAny: " + message);

    }
}
