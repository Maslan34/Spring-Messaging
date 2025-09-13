package com.RabbitMQ_Kafka.messaging.kafka.consumer;


import com.KafkaRabbitMQ.avro.StockAvro;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaStockConsumer {


    @KafkaListener(topics = "avro-topic", groupId = "avro-group")
    public void consume(StockAvro stock) {
        System.out.println("Avro Received: " + stock);
    }
}


