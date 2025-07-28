package com.RabbitMQ_Kafka.Config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("rabbit")
@Configuration
public class TopicExchangeConfig {
    // ### Topic

    @Value("${sample.rabbitmq.dress.exchange}")
    String dressExchange;
    @Value("${sample.rabbitmq.blacks.queue}")
    String blackQueue;
    @Value("${sample.rabbitmq.whites.queue}")
    String whiteQueue;
    @Value("${sample.rabbitmq.colors.queue}")
    String colorQueue;
    @Value("${sample.rabbitmq.delicates.queue}")
    String delicatesQueue;
    @Value("${sample.rabbitmq.allColors.queue}")
    String allColorQueue;


    @Value("${sample.rabbitmq.whites.routingKey}")
    String whiteRoutingKey;
    @Value("${sample.rabbitmq.blacks.routingKey}")
    String blackRoutingKey;
    @Value("${sample.rabbitmq.colors.routingKey}")
    String colorRoutingKey;
    @Value("${sample.rabbitmq.delicates.routingKey}")
    String delicateRoutingKey;
    @Value("${sample.rabbitmq.allColors.routingKey}")
    String allColorRoutingKey;

    // ### Topic ###

    // ### Topic

    @Bean
    public TopicExchange dressExchange() {
        return new TopicExchange(dressExchange);
    }
    @Bean
    public Queue blackQueue() {
        return new Queue(blackQueue,false);
    }
    @Bean
    public Queue whiteQueue() {
        return new Queue(whiteQueue,false);
    }
    @Bean
    public Queue colorQueue() {
        return new Queue(colorQueue,false);
    }
    @Bean
    public Queue delicateQueue() {
        return new Queue(delicatesQueue,false);
    }
    @Bean
    public Queue allColorQueue() {
        return new Queue(allColorQueue,false);
    }


    @Bean
    public Binding bindingBlack(){
        return BindingBuilder.bind(blackQueue()).to(dressExchange()).with(blackRoutingKey);
    }
    @Bean
    public Binding bindingWhite(){
        return BindingBuilder.bind(whiteQueue()).to(dressExchange()).with(whiteRoutingKey);
    }
    @Bean
    public Binding bindingColor(){
        return BindingBuilder.bind(colorQueue()).to(dressExchange()).with(colorRoutingKey);
    }
    @Bean
    public Binding bindingDelicate(){
        return BindingBuilder.bind(delicateQueue()).to(dressExchange()).with(delicateRoutingKey);
    }
    @Bean
    public Binding bindingAllColor(){
        return BindingBuilder.bind(allColorQueue()).to(dressExchange()).with(allColorRoutingKey);
    }

    // ### Topic ###
}
