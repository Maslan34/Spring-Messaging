package com.RabbitMQ_Kafka.Service.rabbit;

import com.RabbitMQ_Kafka.Model.Weather;
import com.RabbitMQ_Kafka.messaging.rabbit.producer.WeatherProducer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("rabbit")
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
