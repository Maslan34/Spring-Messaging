package com.RabbitMQ_Kafka.Config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("rabbit")
@Configuration
public class DirectExchangeConfig {

    // ### Direct
    @Value("${sample.rabbitmq.payment.queue}")
    String paymentQueue;
    @Value("${sample.rabbitmq.shipping.queue}")
    String shippingQueue;
    @Value("${sample.rabbitmq.receipt.queue}")
    String receiptQueue;



    @Value("${sample.rabbitmq.payment.exchange}")
    String paymentExchange;
    @Value("${sample.rabbitmq.shipping.exchange}")
    String shippingExchange;
    @Value("${sample.rabbitmq.receipt.exchange}")
    String receiptExchange;

    @Value("${sample.rabbitmq.payment.routingKey}")
    String paymentRoutingKey;
    @Value("${sample.rabbitmq.shipping.routingKey}")
    String shippingRoutingKey;
    @Value("${sample.rabbitmq.receipt.routingKey}")
    String receiptRoutingKey;

    // ### Direct ###



    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentQueue,false);
    }

    @Bean
    public Queue shippingQueue() {
        return new Queue(shippingQueue,false);
    }

    @Bean
    public Queue receiptQueue() {
        return new Queue(receiptQueue,false);
    }


    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange(paymentExchange);
    }
    @Bean
    public DirectExchange shippingExchange() {
        return new DirectExchange(shippingExchange);
    }

    @Bean
    public DirectExchange receiptExchange() {
        return new DirectExchange(receiptExchange);
    }


    @Bean
    public Binding paymentBinding(Queue paymentQueue, DirectExchange paymentExchange) {
        return BindingBuilder.bind(paymentQueue).to(paymentExchange).with(paymentRoutingKey);
    }

    @Bean
    public Binding shippingBinding(Queue shippingQueue, DirectExchange shippingExchange) {
        return BindingBuilder.bind(shippingQueue).to(shippingExchange).with(shippingRoutingKey);
    }

    @Bean
    public Binding receiptBinding(Queue receiptQueue, DirectExchange receiptExchange) {
        return BindingBuilder.bind(receiptQueue).to(receiptExchange).with(receiptRoutingKey);
    }
}
