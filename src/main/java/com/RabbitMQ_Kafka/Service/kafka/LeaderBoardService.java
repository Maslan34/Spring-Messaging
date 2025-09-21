package com.RabbitMQ_Kafka.Service.kafka;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LeaderBoardService {

    private final Map<String, Integer> scores = new ConcurrentHashMap<>();

    public void updateScore(String playerId, int points) {
        scores.merge(playerId, points, Integer::sum);
        System.out.println("Updated Leaderboard: " + scores);
    }

    public Map<String, Integer> getScores() {
        return scores;
    }
}
