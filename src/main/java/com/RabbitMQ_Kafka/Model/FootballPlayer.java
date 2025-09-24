package com.RabbitMQ_Kafka.Model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "football_players")
@Data
public class FootballPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerName;
    private String fromTeam;
    private String toTeam;
    private BigDecimal fee;
}
