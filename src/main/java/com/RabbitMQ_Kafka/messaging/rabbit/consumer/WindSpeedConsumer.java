package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.WindData;
import com.RabbitMQ_Kafka.Service.rabbit.BridgeService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("rabbit")
@Component
public class WindSpeedConsumer {


    private final BridgeService bridgeService;


    public WindSpeedConsumer(BridgeService bridgeService) {
        this.bridgeService = bridgeService;
    }


    @RabbitListener(queues = "${sample.rabbitmq.circuitbreaker.queue}")
    public void consumeWindData(WindData data) {

        // Get the current state of the CircuitBreaker
        CircuitBreaker.State state = bridgeService.getCircuitBreakerState();
        switch (state) {
            case OPEN:
                System.out.println("CB state: OPEN - Bridge is closed.");
                break;
            case HALF_OPEN:
                System.out.println("CB state: HALF_OPEN - Some lanes were closed. Traffic is moving under control.");
                break;
            case CLOSED:
                System.out.println("CB state: CLOSED - The bridge is open to traffic.");
                break;
        }


        try {
            String result = bridgeService.evaluateWindSpeed(data.getSpeed());
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            String fallbackResult = bridgeService.useAlternativeTransport(data.getSpeed(), e);
            System.out.println("Fallback Result: " + fallbackResult);
        }
    }
}


