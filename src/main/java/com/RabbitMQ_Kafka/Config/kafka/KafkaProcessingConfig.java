package com.RabbitMQ_Kafka.Config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Profile("kafka")
public class KafkaProcessingConfig {

    private final ConsumerFactory<String, String> stringConsumerFactory;


    public KafkaProcessingConfig(
            @Qualifier("stringConsumerFactory") ConsumerFactory<String, String> stringConsumerFactory) {
        this.stringConsumerFactory = stringConsumerFactory;
    }


    // ------------------- Topics -------------------
    @Bean
    public NewTopic atMostTopic() {
        return TopicBuilder.name("game-scores-atmost")
                .partitions(3)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic atLeastTopic() {
        return TopicBuilder.name("game-scores-atleast")
                .partitions(3)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic exactlyOneTopic() {
        return TopicBuilder.name("game-scores-exactly")
                .partitions(3)
                .replicas(1)
                .build();
    }


    // ------------------- TRANSACTION MANAGER -------------------
    @Bean

    public KafkaTransactionManager<String, String> kafkaTransactionManager(
            @Qualifier("stringProducerFactoryExactly") ProducerFactory<String, String> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }


    /* This bean may conflict with other configurations please run it in isolated.
    @Bean
    @Profile("sample_spring_kafka_atleast")
    public ConcurrentKafkaListenerContainerFactory<String, String> atLeastOnceListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

     */


    // AT-MOST
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> atMostOnceListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory);

        //The part that determines how it will acknowledge (or confirm) the messages after processing them.
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);

        // Close retry and error handler
        factory.setCommonErrorHandler(new DefaultErrorHandler((rec, ex) -> {
            // Ignore the message ->  it gets lost
            System.out.println("AT-MOST-ONCE: Message lost due to error: " + rec);
        }, new FixedBackOff(0L, 0L))); // no retry


        return factory;
    }


    // EXACTLY-ONCE
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> exactlyOnceListenerFactory(
            @Qualifier("exactlyOnceConsumerFactory") ConsumerFactory<String, String> consumerFactory,
            KafkaTransactionManager<String, String> kafkaTransactionManager) {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        //Setting the transaction manager with ContainerProperties
        ContainerProperties containerProps = factory.getContainerProperties();
        containerProps.setAckMode(ContainerProperties.AckMode.RECORD);

        containerProps.setKafkaAwareTransactionManager(kafkaTransactionManager);

        return factory;
    }


}

