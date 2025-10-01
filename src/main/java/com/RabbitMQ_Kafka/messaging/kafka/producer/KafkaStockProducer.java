package com.RabbitMQ_Kafka.messaging.kafka.producer;


import com.KafkaRabbitMQ.avro.StockAvro;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka-streams-stock")
public class KafkaStockProducer {

    private final KafkaTemplate<String, StockAvro> kafkaTemplate;

    public KafkaStockProducer(KafkaTemplate<String, StockAvro> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUser(StockAvro avro) {
        kafkaTemplate.send("avro-topic", avro.getSymbol().toString(), avro);
        System.out.println("Avro Sent! " + avro);
    }
}


