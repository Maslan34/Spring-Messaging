package com.RabbitMQ_Kafka.Config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("rabbit")
@Configuration
public class FanoutExchangeConfig {

    // ### Fanout

    @Value("${sample.rabbitmq.animal.exchange}")
    String animalExchange;
    @Value("${sample.rabbitmq.cow.queue}")
    String cowQueue;
    @Value("${sample.rabbitmq.horse.queue}")
    String horseQueue;

    // ### Fanout ###


    //### Fanout



    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(animalExchange);
    }

    @Bean
    public Queue cowQueue() {
        return new Queue(cowQueue,false);
    }

    @Bean
    public Queue horseQueue() {
        return new Queue(horseQueue,false);
    }

    @Bean
    public Binding cowBindingFanout(FanoutExchange exchange, Queue cowQueue) {
        return BindingBuilder.bind(cowQueue).to(exchange);
    }

    @Bean
    public Binding horseBindingFanout(FanoutExchange exchange, Queue horseQueue) {
        return BindingBuilder.bind(horseQueue).to(exchange);
    }
    //### Fanout ###
}
