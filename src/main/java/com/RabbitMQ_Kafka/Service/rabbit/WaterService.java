package com.RabbitMQ_Kafka.Service.rabbit;

import com.RabbitMQ_Kafka.messaging.rabbit.producer.FaucetProducer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("rabbit")
@Service
public class WaterService {

    private final FaucetProducer producer;

    public WaterService(FaucetProducer producer) {
        this.producer = producer;
    }
    public void start(String faucet, int liter) {
        producer.fill(faucet, liter);

    }

}
