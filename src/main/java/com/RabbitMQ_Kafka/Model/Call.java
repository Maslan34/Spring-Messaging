package com.RabbitMQ_Kafka.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Call {

    public String message;
    public int priority;

}
