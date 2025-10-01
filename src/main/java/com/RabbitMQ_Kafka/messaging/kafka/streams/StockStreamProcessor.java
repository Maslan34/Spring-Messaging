package com.RabbitMQ_Kafka.messaging.kafka.streams;


import com.KafkaRabbitMQ.avro.CandleStick;
import com.KafkaRabbitMQ.avro.Company;
import com.KafkaRabbitMQ.avro.StockAvro;
import com.KafkaRabbitMQ.avro.StockStats;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@EnableKafkaStreams
@Profile("kafka-streams-stock")
public class StockStreamProcessor {

    @Bean
    public KStream<String, StockAvro> kStream(
            StreamsBuilder builder,
            SpecificAvroSerde<StockAvro> stockAvroSerde,
            SpecificAvroSerde<StockStats> stockStatsSerde,
            SpecificAvroSerde<CandleStick> candleStickSerde,
            SpecificAvroSerde<Company> companySerde
    ) {

        //  --- First, Get the trade flow. ---
        KStream<String, StockAvro> trades = builder.stream(
                "stock-trades",
                Consumed.with(Serdes.String(), stockAvroSerde)
        );

        // --- Windowed Aggregation (CandleStick) ---
        Duration[] windowSizes = {Duration.ofMinutes(1), Duration.ofMinutes(5), Duration.ofMinutes(10)};
        String[] storeNames = {"candle-store-1min", "candle-store-5min", "candle-store-10min"};
        String[] topicNames = {"candlestick-1min", "candlestick-5min", "candlestick-10min"};

        for (int i = 0; i < windowSizes.length; i++) {
            KTable<Windowed<String>, CandleStick> candle = trades
                    .groupByKey(Grouped.with(Serdes.String(), stockAvroSerde))
                    .windowedBy(TimeWindows.ofSizeWithNoGrace(windowSizes[i]))
                    .aggregate(
                            createCandleStickInitializer(),
                            createCandleStickAggregator(windowSizes[i].toMillis()),
                            Materialized.<String, CandleStick, WindowStore<Bytes, byte[]>>as(storeNames[i])
                                    .withKeySerde(Serdes.String())
                                    .withValueSerde(candleStickSerde)
                    );

            candle.toStream()
                    .to(topicNames[i],
                            Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), candleStickSerde));
        }


        // --- Group by symbol and aggregate. ---
        KTable<String, StockStats> statsTable = trades
                .groupBy((key, trade) -> new String(trade.getSymbol().toString().getBytes())) // <- CharSequence -> String
                .aggregate(
                        // Initializer
                        (Initializer<StockStats>) () -> {
                            StockStats initialStats = new StockStats();
                            initialStats.setSymbol("");
                            initialStats.setTotalQuantity(0);
                            initialStats.setAveragePrice(0.0);
                            initialStats.setCount(0);
                            return initialStats;
                        },
                        // Aggregator
                        (Aggregator<String, StockAvro, StockStats>) (key, trade, agg) -> {
                            int newCount = agg.getCount() + 1;
                            double newAveragePrice = (agg.getAveragePrice() * agg.getCount() + trade.getPrice()) / newCount;

                            return StockStats.newBuilder()
                                    .setSymbol(trade.getSymbol())
                                    .setTotalQuantity(agg.getTotalQuantity() + (int) trade.getVolume()) // <- long -> int cast
                                    .setAveragePrice(newAveragePrice)
                                    .setCount(newCount)
                                    .build();
                        },
                        // Materialized
                        Materialized.<String, StockStats, KeyValueStore<Bytes, byte[]>>as("stock-stats-store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(stockStatsSerde)
                );


        // StockStats KTable -> KStream -> topic
        statsTable.toStream()
                .to("stock-stats", Produced.with(Serdes.String(), stockStatsSerde));

        // --- Join: Trades + Company ---
        KTable<String, Company> companyTable = builder.table(
                "company-info",
                Consumed.with(Serdes.String(), companySerde)
        );

        KStream<String, String> joinedStream = trades.join(
                companyTable,
                (trade, company) -> "TradeSymbol: " + trade.getSymbol() +
                        ", Price: " + trade.getPrice() +
                        ", CompanyName: " + company.getName(),
                Joined.with(Serdes.String(), stockAvroSerde, companySerde)
        );

        joinedStream.to("joined-trades", Produced.with(Serdes.String(), Serdes.String()));

        return trades;
    }

    // --- Helper Methods ---

    // CandleStick initializer
    private Initializer<CandleStick> createCandleStickInitializer() {
        return () -> CandleStick.newBuilder()
                .setSymbol("")
                .setOpenPrice(0)
                .setClosePrice(0)
                .setHighPrice(Double.MIN_VALUE)
                .setLowPrice(Double.MAX_VALUE)
                .setVolume(0)
                .setStartTime(0)
                .setEndTime(0)
                .build();
    }

    // CandleStick aggregator
    private Aggregator<String, StockAvro, CandleStick> createCandleStickAggregator(long windowSizeMillis) {
        return (symbol, trade, agg) -> {

            long tradeTime = trade.getTimestamp().toEpochMilli();
            long windowStart = getWindowStartTime(tradeTime, windowSizeMillis);
            long windowEnd = windowStart + windowSizeMillis;

            double open = (agg.getOpenPrice() == 0) ? trade.getPrice() : agg.getOpenPrice();
            double close = trade.getPrice();
            double high = Math.max(agg.getHighPrice() == Double.MIN_VALUE ? trade.getPrice() : agg.getHighPrice(), trade.getPrice());
            double low = Math.min(agg.getLowPrice() == Double.MAX_VALUE ? trade.getPrice() : agg.getLowPrice(), trade.getPrice());
            long volume = agg.getVolume() + trade.getVolume();

            return CandleStick.newBuilder()
                    .setSymbol(trade.getSymbol())
                    .setOpenPrice(open)
                    .setClosePrice(close)
                    .setHighPrice(high)
                    .setLowPrice(low)
                    .setVolume(volume)
                    .setStartTime(windowStart)
                    .setEndTime(windowEnd)
                    .build();
        };


    }

    private long getWindowStartTime(long timestamp, long windowSize) {
        return timestamp - (timestamp % windowSize);
    }
}
