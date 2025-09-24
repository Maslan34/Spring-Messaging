package com.RabbitMQ_Kafka.Config.kafka;

import jakarta.persistence.EntityManagerFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // We want this annotation to automatically generate the transactionManager bean by JPA.
public class KafkaOutboxPatternConfig {

    @Bean
    public NewTopic playerTransfersTopic() {
        return TopicBuilder.name("player-transfers")
                .build();
    }

    // Using the EntityManagerFactory bean created by JPA.
    private final EntityManagerFactory entityManagerFactory;

    public KafkaOutboxPatternConfig(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}