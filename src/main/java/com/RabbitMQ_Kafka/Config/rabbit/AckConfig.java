package com.RabbitMQ_Kafka.Config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("rabbit")
@Configuration
public class AckConfig {

    @Value("${sample.rabbitmq.tea.exchange}")
    String teaExchange;
    @Value("${sample.rabbitmq.tea.queue}")
    String teaQueue;
    @Value("${sample.rabbitmq.tea.routingKey}")
    String teaRoutingKey;

    MessageConverter messageConverter;

    public AckConfig(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Bean
    public TopicExchange teaExchange() {
        return new TopicExchange(teaExchange);
    }

    @Bean
    public Queue teaQueue() {
        return new Queue(teaQueue, true);
    }

    @Bean
    public Binding bindingTea() {
        return BindingBuilder.bind(teaQueue()).to(teaExchange()).with(teaRoutingKey);
    }


    @Bean
    public SimpleRabbitListenerContainerFactory manualAck(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // MANUAL ACK
        return factory;
    }

}
