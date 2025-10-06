package com.RabbitMQ_Kafka.Service.kafka;


import com.RabbitMQ_Kafka.Model.Trade;
import com.RabbitMQ_Kafka.Model.enums.TradeType;
import com.RabbitMQ_Kafka.messaging.kafka.producer.KafkaTradeProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("kafka-security")
public class TradeService {


    private KafkaTradeProducer tradeProducer;

    public TradeService(KafkaTradeProducer tradeProducer) {
        this.tradeProducer = tradeProducer;
    }

    public void executeLongTrade(String symbol, int quantity, float price, String userId) {
        Trade transaction = new Trade();
        transaction.setSymbol(symbol);
        transaction.setTradeType(TradeType.LONG);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setUserId(userId);

        tradeProducer.sendTradeTransaction(transaction);
    }

    public void executeShortTrade(String symbol, int quantity, float price, String userId) {
        Trade transaction = new Trade();
        transaction.setSymbol(symbol);
        transaction.setTradeType(TradeType.SHORT);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setUserId(userId);

        tradeProducer.sendTradeTransaction(transaction);
    }
}