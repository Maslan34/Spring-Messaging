package com.RabbitMQ_Kafka.Controller;

import com.RabbitMQ_Kafka.Model.Weather;
import com.RabbitMQ_Kafka.Service.WeatherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    public void getWeather(@RequestBody  Weather weather) {
        weatherService.pushWeather(weather);
    }
}
