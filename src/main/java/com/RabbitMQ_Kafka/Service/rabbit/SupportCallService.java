package com.RabbitMQ_Kafka.Service.rabbit;

import com.RabbitMQ_Kafka.Model.Call;
import com.RabbitMQ_Kafka.messaging.rabbit.producer.SupportCallProducer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("rabbit")
@Service
public class SupportCallService {

    private final SupportCallProducer supportCallProducer;

    public SupportCallService(SupportCallProducer supportCallProducer) {
        this.supportCallProducer = supportCallProducer;
    }

    public void call(Call call){
        supportCallProducer.call(call);
    }
}
