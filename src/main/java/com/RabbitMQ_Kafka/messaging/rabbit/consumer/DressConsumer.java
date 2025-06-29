package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Dress;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DressConsumer {

    @RabbitListener(queues = "${sample.rabbitmq.whites.queue}")
    public void whiteDress(Dress dress) {
        System.out.println("Whites--> Dress: " + dress);
        System.out.println("Washed");
    }

    @RabbitListener(queues = "${sample.rabbitmq.blacks.queue}")
    public void blackDress(Dress dress) {
        System.out.println("Blacks--> Dress: " + dress);
        System.out.println("Washed");
    }

    @RabbitListener(queues = "${sample.rabbitmq.colors.queue}")
    public void colorDress(Dress dress) {
        System.out.println("Colors--> Dress: " + dress);
        System.out.println("Washed");
    }

    @RabbitListener(queues = "${sample.rabbitmq.allColors.queue}")
    public void allColorDress(Dress dress) {
        System.out.println("All Colors--> Dress: " + dress);
        System.out.println("Washed");
    }

    @RabbitListener(queues = "${sample.rabbitmq.delicates.queue}")
    public void delicateDress(Dress dress) {
        System.out.println("Delicates-->  Dress: " + dress);
        System.out.println("Washed");
    }
}
