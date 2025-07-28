package com.RabbitMQ_Kafka.Config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("rabbit")
@Configuration
public class SagaDesignConfig {

    // Venue
    @Value("${sample.rabbitmq.saga.venue.success.exchange}")
    private String venueSuccessExchange;

    @Value("${sample.rabbitmq.saga.venue.success.routingKey}")
    private String venueSuccessRoutingKey;

    @Value("${sample.rabbitmq.saga.venue.success.queue}")
    private String venueSuccessQueue;

    @Value("${sample.rabbitmq.saga.venue.fail.exchange}")
    private String venueFailExchange;

    @Value("${sample.rabbitmq.saga.venue.fail.routingKey}")
    private String venueFailRoutingKey;

    @Value("${sample.rabbitmq.saga.venue.fail.queue}")
    private String venueFailQueue;

    // Catering
    @Value("${sample.rabbitmq.saga.catering.success.exchange}")
    private String cateringSuccessExchange;

    @Value("${sample.rabbitmq.saga.catering.success.routingKey}")
    private String cateringSuccessRoutingKey;

    @Value("${sample.rabbitmq.saga.catering.success.queue}")
    private String cateringSuccessQueue;

    @Value("${sample.rabbitmq.saga.catering.fail.exchange}")
    private String cateringFailExchange;

    @Value("${sample.rabbitmq.saga.catering.fail.routingKey}")
    private String cateringFailRoutingKey;

    @Value("${sample.rabbitmq.saga.catering.fail.queue}")
    private String cateringFailQueue;

    // Entertainment
    @Value("${sample.rabbitmq.saga.entertainment.success.exchange}")
    private String entertainmentSuccessExchange;

    @Value("${sample.rabbitmq.saga.entertainment.success.routingKey}")
    private String entertainmentSuccessRoutingKey;

    @Value("${sample.rabbitmq.saga.entertainment.success.queue}")
    private String entertainmentSuccessQueue;

    @Value("${sample.rabbitmq.saga.entertainment.fail.exchange}")
    private String entertainmentFailExchange;

    @Value("${sample.rabbitmq.saga.entertainment.fail.routingKey}")
    private String entertainmentFailRoutingKey;

    @Value("${sample.rabbitmq.saga.entertainment.fail.queue}")
    private String entertainmentFailQueue;

    // Invitation
    @Value("${sample.rabbitmq.saga.invitation.success.exchange}")
    private String invitationSuccessExchange;

    @Value("${sample.rabbitmq.saga.invitation.success.routingKey}")
    private String invitationSuccessRoutingKey;

    @Value("${sample.rabbitmq.saga.invitation.success.queue}")
    private String invitationSuccessQueue;

    @Value("${sample.rabbitmq.saga.invitation.fail.exchange}")
    private String invitationFailExchange;

    @Value("${sample.rabbitmq.saga.invitation.fail.routingKey}")
    private String invitationFailRoutingKey;

    @Value("${sample.rabbitmq.saga.invitation.fail.queue}")
    private String invitationFailQueue;

    // Exchange
    @Bean
    public TopicExchange venueSuccessExchange() {
        return new TopicExchange(venueSuccessExchange);
    }

    @Bean
    public TopicExchange venueFailExchange() {
        return new TopicExchange(venueFailExchange);
    }

    @Bean
    public TopicExchange cateringSuccessExchange() {
        return new TopicExchange(cateringSuccessExchange);
    }

    @Bean
    public TopicExchange cateringFailExchange() {
        return new TopicExchange(cateringFailExchange);
    }

    @Bean
    public TopicExchange entertainmentSuccessExchange() {
        return new TopicExchange(entertainmentSuccessExchange);
    }

    @Bean
    public TopicExchange entertainmentFailExchange() {
        return new TopicExchange(entertainmentFailExchange);
    }

    @Bean
    public TopicExchange invitationSuccessExchange() {
        return new TopicExchange(invitationSuccessExchange);
    }

    @Bean
    public TopicExchange invitationFailExchange() {
        return new TopicExchange(invitationFailExchange);
    }

    // Queue
    @Bean
    public Queue venueSuccessQueue() {
        return new Queue(venueSuccessQueue);
    }

    @Bean
    public Queue venueFailQueue() {
        return new Queue(venueFailQueue);
    }

    @Bean
    public Queue cateringSuccessQueue() {
        return new Queue(cateringSuccessQueue);
    }

    @Bean
    public Queue cateringFailQueue() {
        return new Queue(cateringFailQueue);
    }

    @Bean
    public Queue entertainmentSuccessQueue() {
        return new Queue(entertainmentSuccessQueue);
    }

    @Bean
    public Queue entertainmentFailQueue() {
        return new Queue(entertainmentFailQueue);
    }

    @Bean
    public Queue invitationSuccessQueue() {
        return new Queue(invitationSuccessQueue);
    }

    @Bean
    public Queue invitationFailQueue() {
        return new Queue(invitationFailQueue);
    }

    // Bindings
    @Bean
    public Binding bindingVenueSuccess() {
        return BindingBuilder.bind(venueSuccessQueue()).to(venueSuccessExchange()).with(venueSuccessRoutingKey);
    }

    @Bean
    public Binding bindingVenueFail() {
        return BindingBuilder.bind(venueFailQueue()).to(venueFailExchange()).with(venueFailRoutingKey);
    }

    @Bean
    public Binding bindingCateringSuccess() {
        return BindingBuilder.bind(cateringSuccessQueue()).to(cateringSuccessExchange()).with(cateringSuccessRoutingKey);
    }

    @Bean
    public Binding bindingCateringFail() {
        return BindingBuilder.bind(cateringFailQueue()).to(cateringFailExchange()).with(cateringFailRoutingKey);
    }

    @Bean
    public Binding bindingEntertainmentSuccess() {
        return BindingBuilder.bind(entertainmentSuccessQueue()).to(entertainmentSuccessExchange()).with(entertainmentSuccessRoutingKey);
    }

    @Bean
    public Binding bindingEntertainmentFail() {
        return BindingBuilder.bind(entertainmentFailQueue()).to(entertainmentFailExchange()).with(entertainmentFailRoutingKey);
    }

    @Bean
    public Binding bindingInvitationSuccess() {
        return BindingBuilder.bind(invitationSuccessQueue()).to(invitationSuccessExchange()).with(invitationSuccessRoutingKey);
    }

    @Bean
    public Binding bindingInvitationFail() {
        return BindingBuilder.bind(invitationFailQueue()).to(invitationFailExchange()).with(invitationFailRoutingKey);
    }
}