package com.RabbitMQ_Kafka.Config.kafka;

import com.KafkaRabbitMQ.avro.CandleStick;
import com.KafkaRabbitMQ.avro.Company;
import com.KafkaRabbitMQ.avro.StockAvro;
import com.KafkaRabbitMQ.avro.StockStats;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
@Profile("kafka-streams-stock")
public class KafkaStreamsConfig {


    @Bean
    public NewTopic stockTradesTopic() {
        return TopicBuilder.name("stock-trades")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic companyInfoTopic() {
        return TopicBuilder.name("company-info")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic stockAvroTopic() {
        return TopicBuilder.name("stock-avro-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic stockStatsTopic() {
        return TopicBuilder.name("stock-stats-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic candleStickTopic() {
        return TopicBuilder.name("candlestick-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic candleStickTopic1Min() {
        return TopicBuilder.name("candlestick-1min")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic candleStickTopic5Min() {
        return TopicBuilder.name("candlestick-5min")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic candleStickTopic10Min() {
        return TopicBuilder.name("candlestick-10min")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stock-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class.getName());
        props.put("schema.registry.url", "http://localhost:8082");
        props.put("specific.avro.reader", "true");
        props.put("auto.register.schemas", "true");
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.producerPrefix(ProducerConfig.ACKS_CONFIG), "all");
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);

        return new KafkaStreamsConfiguration(props);
    }


    @Bean
    public SpecificAvroSerde<StockAvro> stockAvroSerde() {
        SpecificAvroSerde<StockAvro> serde = new SpecificAvroSerde<>();
        Map<String, Object> config = new HashMap<>();
        config.put("schema.registry.url", "http://localhost:8082");
        // !!! This is a crucial setting for registering this schema with the Schema Registry.
        config.put("specific.avro.reader", true);
        serde.configure(config, false);
        return serde;
    }

    @Bean
    public SpecificAvroSerde<StockStats> stockStatsSerde() {
        SpecificAvroSerde<StockStats> serde = new SpecificAvroSerde<>();
        Map<String, Object> config = new HashMap<>();
        config.put("schema.registry.url", "http://localhost:8082");
        config.put("specific.avro.reader", true);
        serde.configure(config, false);
        return serde;
    }

    @Bean
    public SpecificAvroSerde<CandleStick> candleStickSerde() {
        SpecificAvroSerde<CandleStick> serde = new SpecificAvroSerde<>();
        Map<String, Object> config = new HashMap<>();
        config.put("schema.registry.url", "http://localhost:8082");
        config.put("specific.avro.reader", true);
        serde.configure(config, false);
        return serde;
    }

    @Bean
    public SpecificAvroSerde<Company> companySerde() {
        SpecificAvroSerde<Company> serde = new SpecificAvroSerde<>();
        Map<String, Object> config = new HashMap<>();
        config.put("schema.registry.url", "http://localhost:8082");
        config.put("specific.avro.reader", true);
        serde.configure(config, false);
        return serde;
    }

    // Common producer configuration
    private Map<String, Object> getCommonProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        config.put("schema.registry.url", "http://localhost:8082");
        config.put("auto.register.schemas", true);
        config.put("use.latest.version", true);
        return config;
    }

    // Common consumer configuration
    private Map<String, Object> getCommonConsumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        config.put("schema.registry.url", "http://localhost:8082");
        config.put("specific.avro.reader", true);
        config.put("auto.register.schemas", true);
        config.put("use.latest.version", true);
        return config;
    }

    /*
    // StockAvro Producer/Consumer
    @Bean
    public ProducerFactory<String, StockAvro> stockAvroProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getCommonProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, StockAvro> stockAvroKafkaTemplate() {
        return new KafkaTemplate<>(stockAvroProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, StockAvro> stockAvroConsumerFactory() {
        Map<String, Object> config = getCommonConsumerConfig();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "stock-avro-group");
        return new DefaultKafkaConsumerFactory<>(config);
    }


     */

    // Company Producer/Consumer
    @Bean
    public ProducerFactory<String, Company> companyProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getCommonProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, Company> companyKafkaTemplate() {
        return new KafkaTemplate<>(companyProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, Company> companyConsumerFactory() {
        Map<String, Object> config = getCommonConsumerConfig();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "company-avro-group");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    // StockStats Producer/Consumer
    @Bean
    public ProducerFactory<String, StockStats> stockStatsProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getCommonProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, StockStats> stockStatsKafkaTemplate() {
        return new KafkaTemplate<>(stockStatsProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, StockStats> stockStatsConsumerFactory() {
        Map<String, Object> config = getCommonConsumerConfig();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "stock-stats-group");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    // CandleStick Producer/Consumer
    @Bean
    public ProducerFactory<String, CandleStick> candleStickProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getCommonProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, CandleStick> candleStickKafkaTemplate() {
        return new KafkaTemplate<>(candleStickProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, CandleStick> candleStickConsumerFactory() {
        Map<String, Object> config = getCommonConsumerConfig();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "candlestick-group");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    // RestTemplate bean for accessing the Schema Registry.
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
