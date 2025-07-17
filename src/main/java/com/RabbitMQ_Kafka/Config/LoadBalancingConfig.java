package com.RabbitMQ_Kafka.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancingConfig {

    @Value("${sample.rabbitmq.loadbalancing.exchange}")
    String loadBalancingExchange;
    @Value("${sample.rabbitmq.loadbalancing.queue}")
    String loadBalancingQueue;
    @Value("${sample.rabbitmq.loadbalancing.routingKey}")
    String loadBalancingRoutingKey;


    @Bean
    public Queue waterQueue() {
        return new Queue(loadBalancingQueue, true);
    }

    @Bean
    public DirectExchange waterExchange() {
        return new DirectExchange(loadBalancingExchange);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(waterQueue()).to(waterExchange()).with(loadBalancingRoutingKey);
    }


}
