package com.RabbitMQ_Kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaRabbitMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaRabbitMqApplication.class, args);
    }

}
