package com.RabbitMQ_Kafka.messaging.rabbit.producer;


import com.RabbitMQ_Kafka.Model.Call;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("rabbit")
@Component
public class SupportCallProducer {

    @Value("${sample.rabbitmq.priority.exchange}")
    String priorityExchange;
    @Value("${sample.rabbitmq.priority.routingKey}")
    String priorityRoutingKey;


    private final RabbitTemplate rabbitTemplate;

    public SupportCallProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void call(Call call) {
        System.out.println("Received Message: " + call);
        rabbitTemplate.convertAndSend(
                priorityExchange,
                priorityRoutingKey,
                call,
                message -> {
                    message.getMessageProperties().setPriority(call.priority);
                    return message;
                }
        );
    }
}
