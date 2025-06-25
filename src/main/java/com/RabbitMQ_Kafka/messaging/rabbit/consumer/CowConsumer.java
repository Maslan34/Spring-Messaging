package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Forage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CowConsumer {
    @RabbitListener(queues = "${sample.rabbitmq.cow.queue}")
    public void feedCow(Forage forage) {
        System.out.println("Feeding Cows: " + forage.toString());

    }

}
