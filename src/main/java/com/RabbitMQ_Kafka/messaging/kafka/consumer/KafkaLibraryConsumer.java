package com.RabbitMQ_Kafka.messaging.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class KafkaLibraryConsumer {


    // By adding this to the Kafka Listener: topicPartitions = @TopicPartition(topic = "novel-topic", partitions = {"2"})
    // Only partition number 2 can be listened to.
    // This way, a separate listener could be created for each genre.

    @KafkaListener(topics = "novel-topic", groupId = "library-group", containerFactory = "stringKafkaListenerFactory")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Received -> Partition: " + record.partition() +
                ", Genre: " + record.key() +
                ", Book: " + record.value());
    }


}

