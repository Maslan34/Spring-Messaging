package com.RabbitMQ_Kafka.Service.rabbit;

import com.RabbitMQ_Kafka.Model.Forage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("rabbit")
@Service
public class FarmService {

    @Value("${sample.rabbitmq.animal.exchange}")
    String animalExchange;


    private final RabbitTemplate rabbitTemplate;



    public FarmService(RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }

    public void feed(Forage forage) {
        System.out.println("Starting to feed all animals with forages.");
        rabbitTemplate.convertAndSend(animalExchange, "", forage);

    }
}
