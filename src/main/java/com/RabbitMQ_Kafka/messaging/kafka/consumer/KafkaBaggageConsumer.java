package com.RabbitMQ_Kafka.messaging.kafka.consumer;

import com.RabbitMQ_Kafka.Model.Baggage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class KafkaBaggageConsumer {


    
    @KafkaListener(topics = "${sample.kafka.offset.topicName}", groupId = "${sample.kafka.offset.groupId}")
    public void receiveBaggage(ConsumerRecord<String, Baggage> record) {
        Baggage baggage = record.value();
        System.out.printf(
                "---> Offset: %d | BaggageID: %s | Passenger: %s | Weight: %.1f kg | Time: %d%n",
                record.offset(),
                baggage.getBaggageId(),
                baggage.getPassengerName(),
                baggage.getWeightKg(),
                baggage.getTimestamp()
        );
    }

}



