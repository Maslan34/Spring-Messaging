package com.RabbitMQ_Kafka.Controller;

import com.RabbitMQ_Kafka.Model.WindData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bridge")
public class DrawBridgeController {


    private final RabbitTemplate rabbitTemplate;

    public DrawBridgeController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${sample.rabbitmq.circuitbreaker.exchange}")
    private String circuitBreakerExchange;

    @Value("${sample.rabbitmq.circuitbreaker.routingKey}")
    private String circuitBreakerRoutingKey;

    @PostMapping
    public ResponseEntity<String> publishWindSpeed(@RequestBody WindData windData) {
        rabbitTemplate.convertAndSend(circuitBreakerExchange, circuitBreakerRoutingKey, windData);
        return ResponseEntity.ok("Wind data has been sent!");

    }


}
