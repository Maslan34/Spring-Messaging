package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Forage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Profile("rabbit")
@Component
public class HorseConsumer {

    @RabbitListener(queues = "${sample.rabbitmq.horse.queue}")
    public void feedHorse(Forage forage) {
        System.out.println("Feeding Horse: " + forage.toString());

    }
}
