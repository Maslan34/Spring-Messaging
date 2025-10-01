package com.RabbitMQ_Kafka.Controller.kafka;

import com.KafkaRabbitMQ.avro.CandleStick;
import com.KafkaRabbitMQ.avro.StockStats;
import com.RabbitMQ_Kafka.messaging.kafka.consumer.KafkaJoinedTradesConsumer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/kafka")
@Profile("kafka-streams-stock")
public class KafkaStockMarketController {

    private final StreamsBuilderFactoryBean factoryBean;
    private final KafkaJoinedTradesConsumer kafkaJoinedTradesConsumer;

    public KafkaStockMarketController(StreamsBuilderFactoryBean factoryBean, KafkaJoinedTradesConsumer kafkaJoinedTradesConsumer) {
        this.factoryBean = factoryBean;
        this.kafkaJoinedTradesConsumer = kafkaJoinedTradesConsumer;

    }

    @GetMapping("/stats")
    public List<Map<String, Object>> getAllStockStats() {
        KafkaStreams streams = waitForRunningState();

        ReadOnlyKeyValueStore<String, StockStats> store = streams.store(
                StoreQueryParameters.fromNameAndType("stock-stats-store", QueryableStoreTypes.keyValueStore())
        );

        List<Map<String, Object>> stats = new ArrayList<>();
        store.all().forEachRemaining(kv -> {
            StockStats stockStats = kv.value;
            Map<String, Object> statMap = new LinkedHashMap<>(); // Sıralı map
            statMap.put("symbol", stockStats.getSymbol().toString());
            statMap.put("totalQuantity", stockStats.getTotalQuantity());
            statMap.put("averagePrice", stockStats.getAveragePrice());
            statMap.put("count", stockStats.getCount());
            stats.add(statMap);
        });
        return stats;
    }

    @GetMapping("/candlestick/{window}/{symbol}")
    public List<Map<String, Object>> getCandles(
            @PathVariable String window,
            @PathVariable String symbol
    ) {
        KafkaStreams streams = factoryBean.getKafkaStreams();

        if (streams == null) {
            throw new IllegalStateException("Kafka Streams is not started.");
        }

        if (streams.state() != KafkaStreams.State.RUNNING) {
            throw new IllegalStateException("Kafka Streams is not in ERROR or RUNNING state. The current state is: " + streams.state());
        }

        // Determine the window size.
        String storeName;
        long windowMillis;
        switch (window) {
            case "1min":
                storeName = "candle-store-1min";
                windowMillis = 60_000L;
                break;
            case "5min":
                storeName = "candle-store-5min";
                windowMillis = 5 * 60_000L;
                break;
            case "10min":
                storeName = "candle-store-10min";
                windowMillis = 10 * 60_000L;
                break;
            default:
                throw new IllegalArgumentException("Invalid window: " + window);
        }

        // Get WindowStore
        ReadOnlyWindowStore<String, CandleStick> store = streams.store(
                StoreQueryParameters.fromNameAndType(storeName, QueryableStoreTypes.windowStore())
        );


        // Windows Start/End Calculation
        long now = System.currentTimeMillis();
        long lastWindowEnd = now - (now % windowMillis);
        long lastWindowStart = lastWindowEnd - windowMillis;

        Instant fromInstant = Instant.ofEpochMilli(lastWindowStart);
        Instant toInstant = Instant.ofEpochMilli(lastWindowEnd);

        List<Map<String, Object>> candles = new ArrayList<>();
        try (WindowStoreIterator<CandleStick> it = store.fetch(symbol, fromInstant, toInstant)) {
            while (it.hasNext()) {
                KeyValue<Long, CandleStick> next = it.next();
                CandleStick candle = next.value;

                Map<String, Object> candleMap = new LinkedHashMap<>();
                candleMap.put("symbol", candle.getSymbol().toString());
                candleMap.put("openPrice", candle.getOpenPrice());
                candleMap.put("closePrice", candle.getClosePrice());
                candleMap.put("highPrice", candle.getHighPrice());
                candleMap.put("lowPrice", candle.getLowPrice());
                candleMap.put("volume", candle.getVolume());
                candleMap.put("startTime", Instant.ofEpochMilli(candle.getStartTime()));
                candleMap.put("endTime", Instant.ofEpochMilli(candle.getEndTime()));
                candleMap.put("timestamp", next.key);

                candles.add(candleMap);
            }
        }

        return candles;
    }

    // --- Join: joined-trades info ---
    @GetMapping("/joined-trades")
    public List<String> joinedTradesInfo() {

        return kafkaJoinedTradesConsumer.getLatestTrades();

    }

    // This function attempts to retrieve the stream for 10 seconds,
    // either during the stream's startup or in any other situation.
    private KafkaStreams waitForRunningState() {
        KafkaStreams streams = factoryBean.getKafkaStreams();
        if (streams == null) {
            throw new IllegalStateException("Kafka Streams is not started!");
        }


        for (int i = 0; i < 10; i++) {
            if (streams.state() == KafkaStreams.State.RUNNING) {
                return streams;
            }
            try {
                TimeUnit.SECONDS.sleep(1); // Wait 1 sc
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted while waiting.", e);
            }
        }
        throw new IllegalStateException("Kafka Streams failed to transition to the RUNNING state within 10 seconds. The current state is: " + streams.state());
    }


}
