package com.RabbitMQ_Kafka.Config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;


@Profile("rabbit")
@Configuration
public class HeadersExchangeConfig {

    @Value("${sample.rabbitmq.weather.exchange}")
    String weatherExchange;
    @Value("${sample.rabbitmq.ege.storm.all.queue}")
    String egeStormAllQueue;
    @Value("${sample.rabbitmq.ege.any.queue}")
    String egeAnyQueue;
    @Value("${sample.rabbitmq.blacksea.storm.all.queue}")
    String blackseaStormAllQueue;
    @Value("${sample.rabbitmq.blacksea.any.queue}")
    String blackseaAnyQueue;

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
    public Queue blackseaStormAllQueue() {
        return new Queue(blackseaStormAllQueue, false);
    }

    @Bean
    public Queue blackseaAnyQueue() {
        return new Queue(blackseaAnyQueue,false);
    }



    @Bean
    public Binding bindingEgeAll(Queue egeStormAllQueue, HeadersExchange weatherExchange) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("region", "ege");
        headers.put("event", "storm");

        return BindingBuilder.bind(egeStormAllQueue)
                .to(weatherExchange)
                .whereAll(headers) // The 'all' here basically acts like 'and'.

                .match();
    }

    @Bean
    public Binding bindingEgeAny(Queue egeAnyQueue, HeadersExchange weatherExchange) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("region", "ege");

        return BindingBuilder.bind(egeAnyQueue)
                .to(weatherExchange)
                .whereAny(headers) // // The 'any' here basically acts like 'or'.
                .match();
    }

    @Bean
    public Binding bindingBlackSeaStormAll(Queue blackseaStormAllQueue, HeadersExchange weatherExchange) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("region", "blacksea");
        headers.put("event", "storm");

        return BindingBuilder.bind(blackseaStormAllQueue)
                .to(weatherExchange)
                .whereAll(headers)
                .match();
    }

    @Bean
    public Binding blackseaAny(Queue blackseaAnyQueue, HeadersExchange weatherExchange) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("region", "blacksea");

        return BindingBuilder.bind(blackseaAnyQueue)
                .to(weatherExchange)
                .whereAny(headers)
                .match();
    }

}
