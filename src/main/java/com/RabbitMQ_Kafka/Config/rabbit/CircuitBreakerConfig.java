package com.RabbitMQ_Kafka.Config.rabbit;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
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
public class CircuitBreakerConfig {


    @Value("${sample.rabbitmq.circuitbreaker.exchange}")
    private String circuitBreakerExchange;

    @Value("${sample.rabbitmq.circuitbreaker.queue}")
    private String circuitBreakerQueue;

    @Value("${sample.rabbitmq.circuitbreaker.routingKey}")
    private String circuitBreakerRoutingKey;

    @Bean
    public TopicExchange bridgeExchange() {
        return new TopicExchange(circuitBreakerExchange);
    }

    // Queue
    @Bean
    public Queue bridgeQueue() {
        return new Queue(circuitBreakerQueue);
    }


    @Bean
    public Binding bindingBridge(Queue bridgeQueue, TopicExchange bridgeExchange) {
        return BindingBuilder.bind(bridgeQueue).to(bridgeExchange).with(circuitBreakerRoutingKey);
    }

    @Bean
    public CircuitBreaker bridgeControlCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("bridgeControlCB");
    }
}


