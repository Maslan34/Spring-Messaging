package com.RabbitMQ_Kafka.Model;

import lombok.Data;

@Data
public class Weather {

    private String cityName;
    private String region;
    private String event;
    private Float temperature;

}
