package com.RabbitMQ_Kafka.Config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    // Fanout

    @Value("${sample.rabbitmq.animal.exchange}")
    String animalExchange;
    @Value("${sample.rabbitmq.cow.queue}")
    String cowQueue;
    @Value("${sample.rabbitmq.horse.queue}")
    String horseQueue;

    // Fanout

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    // Fanout

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

    // Fanout

}
