package com.RabbitMQ_Kafka.Service.rabbit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Profile("rabbit")
@Service
public class AlternativeTransportService {

    public String fallback(double speed, Throwable t) {
        System.out.println("(Wind: " + speed + " m/s). Traffic has been redirected to alternative routes.");
        return "(Wind: " + speed + " m/s). Traffic has been redirected to alternative routes.";

    }
}
