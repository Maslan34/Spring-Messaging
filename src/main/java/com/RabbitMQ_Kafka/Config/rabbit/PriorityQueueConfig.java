package com.RabbitMQ_Kafka.Config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;


@Profile("rabbit")
@Configuration
public class PriorityQueueConfig {


    @Value("${sample.rabbitmq.priority.exchange}")
    String priorityExchange;
    @Value("${sample.rabbitmq.priority.queue}")
    String priorityQueue;
    @Value("${sample.rabbitmq.priority.routingKey}")
    String priorityRoutingKey;


    @Bean
    public TopicExchange priorityExchange() {
        return new TopicExchange(priorityExchange);
    }

    @Bean
    public Queue priorityQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", 24); // maximum priority level is determined.
        return new Queue(priorityQueue, true, false, false, args);
    }

    @Bean
    public Binding bindingPriority(){
        return BindingBuilder.bind(priorityQueue()).to(priorityExchange()).with(priorityRoutingKey);
    }

    @Bean(name = "priority")
    public SimpleRabbitListenerContainerFactory priority(ConnectionFactory connectionFactory) {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 3 definitions here are required for priority to work properly.
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // It does not deliver one message until the next one is fully processed. This is necessary for the concept of priority.
        factory.setPrefetchCount(1);
        return factory;
    }
}
