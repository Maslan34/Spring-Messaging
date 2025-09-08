package com.RabbitMQ_Kafka.messaging.kafka.producer;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaLibraryProducer {


    private KafkaTemplate<String, String> kafkaTemplate;

    private final String TOPIC_NAME ="novel-topic";

    public KafkaLibraryProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }


    public void sendBook(String genre, String bookName) {

    // Normally, partitions are determined by Kafka's algorithm
    // Partition = key.hashCode() % partitionCount, which distributes messages across partitions.
    // Here, messages are sent randomly, meaning the same genres can end up in different partitions.
    // However, this could be further improved so that each genre always goes to a single partition.

        int partition = (int) (Math.random() * 3); // 0, 1, or 2
        kafkaTemplate.send(new ProducerRecord<>(TOPIC_NAME, partition, genre, bookName));
        System.out.println("Sent  -> Genre: " + genre + ", Book: " + bookName);
    }
}


