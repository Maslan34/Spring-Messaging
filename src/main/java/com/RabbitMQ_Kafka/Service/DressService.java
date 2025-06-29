package com.RabbitMQ_Kafka.Service;

import com.RabbitMQ_Kafka.Model.Dress;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DressService {

    @Value("${sample.rabbitmq.dress.exchange}")
    String dressExchange;

    @Value("${sample.rabbitmq.whites.routingKey}")
    String whiteRoutingKey;
    @Value("${sample.rabbitmq.blacks.routingKey}")
    String blackRoutingKey;
    @Value("${sample.rabbitmq.colors.routingKey}")
    String colorRoutingKey;



    private final RabbitTemplate rabbitTemplate;
    public DressService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void seperate(Dress dress) {
        //System.out.println("error: "+dress);

        if (dress.getIsDelicate())
            rabbitTemplate.convertAndSend(dressExchange,"color.color.delicate", dress);
        else{
            //System.out.println("Color: "+dress.getColor());
            switch (dress.getColor()) {
                case BLACK:
                    rabbitTemplate.convertAndSend(dressExchange,blackRoutingKey, dress);
                    break;
                case WHITE:
                    rabbitTemplate.convertAndSend(dressExchange,whiteRoutingKey, dress);
                    break;
                default:
                    rabbitTemplate.convertAndSend(dressExchange,colorRoutingKey, dress);
                    break;
            }
        }
    }
}
