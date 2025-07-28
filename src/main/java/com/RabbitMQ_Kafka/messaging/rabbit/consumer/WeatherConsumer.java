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

    @RabbitListener(queues = "${sample.rabbitmq.karadeniz.storm.all.queue}")
    public void handleKaradenizStormAll(byte[] msg) {
        String message = new String(msg, StandardCharsets.UTF_8);
        System.out.println("handleKaradenizStormAll: " + message);

    }

    @RabbitListener(queues = "${sample.rabbitmq.karadeniz.any.queue}")
    public void handleKaradenizAny(byte[] msg) {
        String message = new String(msg, StandardCharsets.UTF_8);
        System.out.println("handleKaradenizAny: " + message);

    }
}
