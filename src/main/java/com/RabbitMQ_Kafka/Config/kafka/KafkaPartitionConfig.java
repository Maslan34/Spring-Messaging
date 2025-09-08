package com.RabbitMQ_Kafka.Config.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaPartitionConfig {

    @Bean
    public NewTopic romanTopic() {
        return TopicBuilder.name("novel-topic")
                .partitions(3)
                // Replica defines how many copies each partition will have.
                // Here, these 3 replicas are evenly distributed across 3 brokers to ensure data safety.
                .replicas(3)
                .build();
    }
}

