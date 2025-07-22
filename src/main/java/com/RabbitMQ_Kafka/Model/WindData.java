package com.RabbitMQ_Kafka.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WindData {

    private double speed;
    private LocalDateTime timestamp;

}
