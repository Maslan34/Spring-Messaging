package com.RabbitMQ_Kafka.Config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("kafka")
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    // -------- String  --------
    @Bean
    @Primary
    // This setting is for Transactional. When the KafkaTransactionManager Bean is created, it needs a producer factory bean. Since there are two here, we made this one primary to ensure it is selected.
    public ProducerFactory<String, String> stringProducerFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @Primary
    public KafkaTemplate<String, String> stringKafkaTemplate() {
        return new KafkaTemplate<>(stringProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> stringKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory());
        return factory;
    }


    // -------- Object --------

    @Bean
    public ProducerFactory<String, Object> genericProducerFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplateGeneric() {
        return new KafkaTemplate<>(genericProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, Object> genericConsumerFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        // Used the TypeId value in the header of request to determine the object type for deserialization.
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> genericKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(genericConsumerFactory());
        return factory;
    }


    // Producer Factory For Exactly
    @Bean
    public ProducerFactory<String, String> stringProducerFactoryExactly() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);

        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(props);
        factory.setTransactionIdPrefix("game-tx-");
        return factory;
    }


    @Bean
    public ConsumerFactory<String, String> exactlyOnceConsumerFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringDeserializer.class);

        // The isolation level must be manually set to read_committed in the properties.
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> transactionalKafkaTemplate() {
        KafkaTemplate<String, String> template = new KafkaTemplate<>(stringProducerFactoryExactly());
        // Special settings for the transactional template
        return template;
    }

    // Special producer factory for At-Most-Once (without retries)
    @Bean
    public ProducerFactory<String, String> atMostOnceProducerFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);

        // Special settings for At-Most-Once
        props.put(ProducerConfig.ACKS_CONFIG, "1");  // Acknowledge from leader only
        props.put(ProducerConfig.RETRIES_CONFIG, 0); // No retries
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false); // Idempotence is off
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 1000); // Short timeout

        return new DefaultKafkaProducerFactory<>(props);
    }

    // Special producer factory for At-Least-Once (high retry)
    @Bean
    public ProducerFactory<String, String> atLeastOnceProducerFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringDeserializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringDeserializer.class);

        // Special settings for At-Least-Once
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // Acknowledge from all replicas
        props.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE); // Maximum retries
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000); // Wait time between retries
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000); // Long timeout
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 120000); // Total delivery timeout

        return new DefaultKafkaProducerFactory<>(props);
    }
}