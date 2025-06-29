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

    // ### Fanout

    @Value("${sample.rabbitmq.animal.exchange}")
    String animalExchange;
    @Value("${sample.rabbitmq.cow.queue}")
    String cowQueue;
    @Value("${sample.rabbitmq.horse.queue}")
    String horseQueue;

    // ### Fanout ###

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
    public TopicExchange paymentExchange() {
        return new TopicExchange(paymentExchange);
    }
    @Bean
    public TopicExchange shippingExchange() {
        return new TopicExchange(shippingExchange);
    }

    @Bean
    public TopicExchange receiptExchange() {
        return new TopicExchange(receiptExchange);
    }


    @Bean
    public Binding paymentBinding(Queue paymentQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentQueue).to(paymentExchange).with(paymentRoutingKey);
    }

    @Bean
    public Binding shippingBinding(Queue shippingQueue, TopicExchange shippingExchange) {
        return BindingBuilder.bind(shippingQueue).to(shippingExchange).with(shippingRoutingKey);
    }

    @Bean
    public Binding receiptBinding(Queue receiptQueue, TopicExchange receiptExchange) {
        return BindingBuilder.bind(receiptQueue).to(receiptExchange).with(receiptRoutingKey);
    }

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
