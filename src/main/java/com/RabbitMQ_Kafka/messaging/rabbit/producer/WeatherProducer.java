package com.RabbitMQ_Kafka.messaging.rabbit.producer;

import com.RabbitMQ_Kafka.Model.Weather;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class WeatherProducer {

    @Value("${sample.rabbitmq.weather.exchange}")
    String weatherExchange;

    private final RabbitTemplate rabbitTemplate;

    public WeatherProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void pushWeather(Weather weather) {

        // Burada convertAndSend kullanmıyoruz çünkü artık header kımsını biz manuel olarak değiştiriyoruz.
        // Diğer türlü kafka bunu bizim için yapıyordu.

        MessageProperties props = new MessageProperties();
        String msg;

        System.out.println(weather);
        if(weather.getRegion().equals("KARADENIZ") && weather.getEvent().equals("FIRTINA")){
             msg = "⛈️ Thunderstorm warning in the Black Sea region.Temperature: " +weather.getTemperature();
            props.setHeader("region", "karadeniz");
            props.setHeader("event", "firtina");
        }else if(weather.getRegion().equals("EGE") && weather.getEvent().equals("FIRTINA")){
            msg = "⛈️ Thunderstorm warning in the Ege region.Temperature: " +weather.getTemperature();
            props.setHeader("region", "ege");
            props.setHeader("event", "firtina");
        }
        else{
            msg = "A weather event:.Temperature: " +weather.getTemperature();
            props.setHeader("region", weather.getRegion().toLowerCase());
            props.setHeader("event", weather.getEvent().toLowerCase());
        }

        Message message = new Message(msg.getBytes(StandardCharsets.UTF_8), props);

        rabbitTemplate.send(weatherExchange, "", message);


    }

}
