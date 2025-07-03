package com.RabbitMQ_Kafka.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HeadersExchangeConfig {

    @Value("${sample.rabbitmq.weather.exchange}")
    String weatherExchange;
    @Value("${sample.rabbitmq.ege.storm.all.queue}")
    String egeStormAllQueue;
    @Value("${sample.rabbitmq.ege.any.queue}")
    String egeAnyQueue;
    @Value("${sample.rabbitmq.karadeniz.storm.all.queue}")
    String karadenizStormAllQueue;
    @Value("${sample.rabbitmq.karadeniz.any.queue}")
    String karadenizAnyQueue;

    @Bean
    public HeadersExchange weatherExchange() {
        return new HeadersExchange(weatherExchange);
    }

    @Bean
    public Queue egeStormAllQueue() {
        return new Queue(egeStormAllQueue,false);
    }

    @Bean
    public Queue egeAnyQueue() {
        return new Queue(egeAnyQueue,false);
    }

    @Bean
    public Queue karadenizStormAllQueue() {
        return new Queue(karadenizStormAllQueue,false);
    }

    @Bean
    public Queue karadenizAnyQueue() {
        return new Queue(karadenizAnyQueue,false);
    }


    @Bean
    public Binding bindingEgeAll(Queue egeStormAllQueue, HeadersExchange weatherExchange) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("region", "ege");
        headers.put("event", "storm");

        return BindingBuilder.bind(egeStormAllQueue)
                .to(weatherExchange)
                .whereAll(headers)
                .match();
    }

    @Bean
    public Binding bindingEgeAny(Queue egeAnyQueue, HeadersExchange weatherExchange) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("region", "ege");
        headers.put("event", "storm");

        return BindingBuilder.bind(egeAnyQueue)
                .to(weatherExchange)
                .whereAny(headers)
                .match();
    }

    @Bean
    public Binding bindingKaradenizFirtinaAll(Queue karadenizStormAllQueue, HeadersExchange weatherExchange) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("region", "karadeniz");
        headers.put("event", "storm");

        return BindingBuilder.bind(karadenizStormAllQueue)
                .to(weatherExchange)
                .whereAll(headers)
                .match();
    }

    @Bean
    public Binding karadenizAny(Queue karadenizAnyQueue, HeadersExchange weatherExchange) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("region", "karadeniz");
        headers.put("event", "storm");

        return BindingBuilder.bind(karadenizAnyQueue)
                .to(weatherExchange)
                .whereAny(headers)
                .match();
    }

}
