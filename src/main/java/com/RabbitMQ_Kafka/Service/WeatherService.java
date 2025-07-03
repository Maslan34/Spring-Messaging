package com.RabbitMQ_Kafka.Service;

import com.RabbitMQ_Kafka.Model.Weather;
import com.RabbitMQ_Kafka.messaging.rabbit.producer.WeatherProducer;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {


    private final WeatherProducer weatherProducer;

    public WeatherService(WeatherProducer weatherProducer) {
        this.weatherProducer = weatherProducer;
    }

    public void pushWeather(Weather weather) {
        weatherProducer.pushWeather(weather);
    }

}
