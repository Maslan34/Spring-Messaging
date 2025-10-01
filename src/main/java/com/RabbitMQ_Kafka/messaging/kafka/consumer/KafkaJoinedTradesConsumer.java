package com.RabbitMQ_Kafka.messaging.kafka.consumer;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Profile("kafka-streams-stock")
public class KafkaJoinedTradesConsumer {

    // Using a thread-safe list because multiple threads can access this list.
    private final List<String> joinedTrades = new CopyOnWriteArrayList<>();
    private static final int MAX_MESSAGES = 100;

    @KafkaListener(topics = "joined-trades", groupId = "joined-trades-group",containerFactory = "stringKafkaListenerFactory")
    public void consume(String message) {
        // Add the incoming message to the beginning of the list.
        joinedTrades.add(0, message);

       //Check the list size and delete old messages.
        if (joinedTrades.size() > MAX_MESSAGES) {
            joinedTrades.remove(MAX_MESSAGES);
        }
        System.out.println("Joined Trade Received: " + message);
    }

    public List<String> getLatestTrades() {
        return joinedTrades;
    }


}
