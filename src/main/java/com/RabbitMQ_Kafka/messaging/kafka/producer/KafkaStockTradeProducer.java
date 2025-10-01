package com.RabbitMQ_Kafka.messaging.kafka.producer;


import com.KafkaRabbitMQ.avro.Company;
import com.KafkaRabbitMQ.avro.StockAvro;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Profile("kafka-streams-stock")
public class KafkaStockTradeProducer {

    private final KafkaTemplate<String, StockAvro> kafkaTemplate;
    private final KafkaTemplate<String, Company> kafkaTemplateCompany;
    private final Random random = new Random();
    private final String[] symbols = {"AAPL", "GOOG", "MSFT"};

    public KafkaStockTradeProducer(KafkaTemplate<String, StockAvro> kafkaTemplate, KafkaTemplate<String, Company> kafkaTemplateCompany) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateCompany = kafkaTemplateCompany;
        sendCompanyInfo(); // For Join
        openStockMarket(); // Starting produce stock market data
    }

    // Here, Simulating stock trade producer continuously generating random trade data (symbol, price, volume, and timestamp)
    // every second and publishing it to a Kafka topic.
    private void openStockMarket() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            String symbol = symbols[random.nextInt(symbols.length)];
            double price = 100 + random.nextDouble() * 2000;
            long qty = random.nextLong(500) + 1;
            long ts = System.currentTimeMillis();

            StockAvro trade = new StockAvro();
            trade.setSymbol(symbol);
            trade.setPrice(price);
            trade.setVolume(qty);
            trade.setTimestamp(Instant.ofEpochMilli(ts));
            kafkaTemplate.send("stock-trades", symbol, trade);
        }, 0, 1, TimeUnit.SECONDS);
    }

    // --- Automatically sending data to the topic. ---
    private void sendCompanyInfo() {
        System.out.println("Starting to send initial company info to Kafka...");

        // Örnek şirket verilerini oluştur
        Company aapl = new Company("AAPL", "Apple Inc.", "Technology");
        Company goog = new Company("GOOG", "Alphabet Inc.", "Technology");
        Company msft = new Company("MSFT", "Microsoft Corporation", "Technology");

        // Verileri ilgili Kafka topic'ine gönder
        kafkaTemplateCompany.send("company-info", aapl.getSymbol().toString(), aapl);
        kafkaTemplateCompany.send("company-info", goog.getSymbol().toString(), goog);
        kafkaTemplateCompany.send("company-info", msft.getSymbol().toString(), msft);

        System.out.println("Initial company info sent successfully.");
    }
}

