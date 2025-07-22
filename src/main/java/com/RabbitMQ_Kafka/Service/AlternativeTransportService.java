package com.RabbitMQ_Kafka.Service;

import org.springframework.stereotype.Service;


@Service
public class AlternativeTransportService {

    public String fallback(double speed, Throwable t) {
        System.out.println("(Wind: " + speed + " m/s). Traffic has been redirected to alternative routes.");
        return "(Wind: " + speed + " m/s). Traffic has been redirected to alternative routes.";

    }
}
