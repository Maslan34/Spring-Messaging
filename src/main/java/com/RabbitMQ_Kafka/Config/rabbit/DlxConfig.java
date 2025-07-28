package com.RabbitMQ_Kafka.Config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("rabbit")
@Configuration
public class DlxConfig {

    @Value("${sample.rabbitmq.dlx.mail.exchange}")
    String dlxExchange;
    @Value("${sample.rabbitmq.dlx.mail.queue}")
    String dlxQueue;
    @Value("${sample.rabbitmq.dlx.mail.routingKey}")
    String dlxRoutingKey;

    @Value("${sample.rabbitmq.mail.exchange}")
    String mailExchange;
    @Value("${sample.rabbitmq.mail.queue}")
    String mailQueue;
    @Value("${sample.rabbitmq.mail.routingKey}")
    String mailRoutingKey;


    @Bean
    public DirectExchange mailExchange() {
        return new DirectExchange(mailExchange);
    }


    // Building with Builder
    @Bean
    public Queue mailQueue() {
        return QueueBuilder.durable(mailQueue)
                .withArgument("x-dead-letter-exchange", dlxExchange)
                .withArgument("x-dead-letter-routing-key", dlxRoutingKey)
                .build();
    }
    // Alternative Declaration

    /*

    @Bean
    public Queue mailQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", dlxExchange);
        args.put("x-dead-letter-routing-key", dlxRoutingKey);

        return new Queue("mailQueue", true, false, false, args);
    }

     */

    @Bean
    public Binding mailBinding() {
        return BindingBuilder
                .bind(mailQueue())
                .to(mailExchange())
                .with(mailRoutingKey);
    }



    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(dlxExchange);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(dlxQueue);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(dlxRoutingKey);
    }


}
