package com.RabbitMQ_Kafka.Service.rabbit;

import com.RabbitMQ_Kafka.Model.Tea;
import com.RabbitMQ_Kafka.messaging.rabbit.producer.TeaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Profile("rabbit")
@Service
public class TeaService {

    @Value("${sample.rabbitmq.tea.exchange}")
    String teaExchange;

    private final TeaProducer teaProducer;

    public TeaService(TeaProducer teaProducer) {

        this.teaProducer =teaProducer;
    }


    public ResponseEntity<Boolean> brew(Tea tea){
         teaProducer.brewTea(tea);
        return ResponseEntity.ok(true);
    }

}
