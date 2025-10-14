package com.RabbitMQ_Kafka.Config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("kafka")
public class KafkaRetryMechanismConfig {

    @Bean
    public NewTopic ticketPurchaseRequestsTopic() {
        return TopicBuilder.name("ticket-purchase-requests")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ticketPurchaseRetryTopic() {
        return TopicBuilder.name("ticket-purchase-retry")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ticketPurchaseDltTopic() {
        return TopicBuilder.name("ticket-purchase-dlt")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
