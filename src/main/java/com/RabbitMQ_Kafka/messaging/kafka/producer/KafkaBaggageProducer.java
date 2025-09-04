package com.RabbitMQ_Kafka.messaging.kafka.producer;

import com.RabbitMQ_Kafka.Model.Baggage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaBaggageProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Value("${sample.kafka.offset.topicName}")
    private String topicName;

    public KafkaBaggageProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private final String[] names = {"Ayse", "Mehmet", "Fatma", "Ali", "Zeynep", "Ahmet"};

    public void sendBaggage() {
        for (int i = 0; i < 10; i++) {
            Baggage baggage = new Baggage();
            baggage.setBaggageId(UUID.randomUUID().toString());
            baggage.setPassengerName(names[(int) (Math.random() * names.length)]);
            baggage.setWeightKg(Math.round((10 + Math.random() * 20) * 10.0) / 10.0);
            baggage.setTimestamp(System.currentTimeMillis());

            kafkaTemplate.send(topicName, baggage);
            System.out.println("Baggage sent: " + baggage.getBaggageId() + " (" + baggage.getPassengerName() + ")");
        }
    }
}


