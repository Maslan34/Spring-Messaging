package com.RabbitMQ_Kafka.Model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
@Data
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType; // PlayerTransfer
    private Long aggregateId;
    private String eventType; // PlayerTransferred

    @Column(name = "payload", columnDefinition = "TEXT") // FootballPlayer Object Json
    private String payload;
    private Boolean processed = false;
}

