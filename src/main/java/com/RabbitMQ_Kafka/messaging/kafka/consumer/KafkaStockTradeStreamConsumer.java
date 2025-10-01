package com.RabbitMQ_Kafka.messaging.kafka.consumer;



import com.KafkaRabbitMQ.avro.StockStats;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka-streams-stock")
public class KafkaStockTradeStreamConsumer {

    @KafkaListener(topics = "stock-stats", groupId = "stock-stats-group")
    public void consume(StockStats stats) {
        System.out.println("Symbol: " + stats.getSymbol()
                + " | TotalQty: " + stats.getTotalQuantity()
                + " | AvgPrice: " + stats.getAveragePrice());
    }
}
