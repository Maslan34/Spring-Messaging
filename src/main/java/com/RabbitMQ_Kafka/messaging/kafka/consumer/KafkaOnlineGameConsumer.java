package com.RabbitMQ_Kafka.messaging.kafka.consumer;

import com.RabbitMQ_Kafka.Service.kafka.LeaderBoardService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Component
public class KafkaOnlineGameConsumer {

    private final LeaderBoardService leaderboardService;
    private final Random random = new Random();

    public KafkaOnlineGameConsumer(LeaderBoardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }


    /** AT-LEAST-ONCE: The message is never lost, it can be duplicated.
     * This listener uses manual acknowledgment.
     * * If ack.acknowledge() is not called after the message is successfully processed,
     * * Kafka considers the message unprocessed and resends it.
     * * This prevents data loss but may create duplicates.
     **/


    /* Please run it in isolated.
    @KafkaListener(topics = "game-scores-atleast", groupId = "leaderboard-atleast", containerFactory = "atLeastOnceListenerFactory")
    @Profile("sample_spring_kafka_atleast")
    public void handleAtLeastOnceMessage(String event, Acknowledgment ack) {
        System.out.println("AT-LEAST-ONCE: Processing " + event);

        try {

            String[] parts = event.split(":");
            String playerId = parts[0];
            int points = Integer.parseInt(parts[1]);

            System.out.println("From " + playerId + " player received " + points + " points");


            if (random.nextInt(100) < 30) {
                System.out.println("AT-LEAST-ONCE: Crash before ack! Message will be reprocessed.");
                throw new RuntimeException("Simulated crash - message will be reprocessed!");
            }

            leaderboardService.updateScore(playerId, points);

          // If the operation is successful, call ACK to advance the offset.
            ack.acknowledge();
            System.out.println("AT-LEAST-ONCE: Successfully processed and acknowledged.");

        } catch (Exception e) {
            System.err.println("AT-LEAST-ONCE: Error occurred, message will be reprocessed: " + e.getMessage());
            // When an exception is thrown, the message is not acknowledged, causing Kafka to redeliver it.
            throw e;
        }
    }

    */


    /**
     * AT-MOST-ONCE: The message may be lost, but it will never be duplicated.
     * This listener is suitable for 'fire-and-forget' scenarios,
     * as it uses automatic offset committing and advances the offset even if an error occurs,
     * which carries the risk of losing the message.
     */

    @KafkaListener(topics = "game-scores-atmost", groupId = "leaderboard-atmost", containerFactory = "atMostOnceListenerFactory")
    public void handleAtMostOnceMessage(String event) {
        System.out.println("AT-MOST-ONCE: Processing " + event);

        try {

            String[] parts = event.split(":");
            String playerId = parts[0];
            int points = Integer.parseInt(parts[1]);

            System.out.println("From " + playerId + " player received " + points + " points");

            if (random.nextInt(100) < 30) {
                System.out.println("AT-MOST-ONCE: Crash! Message will be lost.");
                throw new RuntimeException("Simulated crash - message lost!");
            }

            // Skor güncellemesi
            leaderboardService.updateScore(playerId, points);

            System.out.println("AT-MOST-ONCE: Successfully processed.");

        } catch (Exception e) {
            System.err.println("AT-MOST-ONCE: Error occurred, message will be lost: " + e.getMessage());
            // The error is not handled, the offset is automatically advanced and the message disappears.
        }
    }


    /**
     * EXACTLY-ONCE: The message is processed exactly once
     * - Transactional processing
     * - Idempotent operations
     */

    @KafkaListener(topics = "game-scores-exactly", groupId = "leaderboard-exactly",
            containerFactory = "exactlyOnceListenerFactory")
    @Transactional("kafkaTransactionManager")
    public void calculateScoreExactly(String event) {
        System.out.println("EXACTLY-ONCE: Processing " + event);

        try {
            String[] parts = event.split(":");
            String playerId = parts[0];
            int points = Integer.parseInt(parts[1]);

            System.out.println("From " + playerId + " player received " + points + " points");

            // Crash is simulated
            if (random.nextInt(100) < 30) {
                System.out.println("EXACTLY-ONCE: Crash! Transaction will be rolled back");
                throw new RuntimeException("Simulated crash - transaction rollback!");
            }

            leaderboardService.updateScore(playerId, points);

            System.out.println("EXACTLY-ONCE: Successfully processed in transaction");

        } catch (Exception e) {
            System.out.println("EXACTLY-ONCE: Error occurred, transaction will be rolled back: " + e.getMessage());
            throw e; // Throw exception for transaction rollback
        }
    }
}