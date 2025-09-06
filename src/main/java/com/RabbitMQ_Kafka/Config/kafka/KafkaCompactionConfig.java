package com.RabbitMQ_Kafka.Config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaCompactionConfig {

    @Bean
    public NewTopic greengrocerTopicCompact() {
        return TopicBuilder.name("greengrocer-compact")
                .partitions(1)
                .replicas(1)
                .config("cleanup.policy", "compact")
                .build();
    }

    @Bean
    public NewTopic greengrocerTopicDelete() {
        return TopicBuilder.name("greengrocer-delete")
                .partitions(1)
                .replicas(1)
                .config("cleanup.policy", "delete")
                .config("retention.ms", "30000") // message remains average 30 seconds
                .build();
    }
}
