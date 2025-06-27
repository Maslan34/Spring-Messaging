package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiptConsumer {


    @RabbitListener(queues = "${sample.rabbitmq.receipt.queue}")
    public void handleReceipt(Order order) {
        System.out.println("Created Receipt: " + order.toString());
        System.out.println("Transaction Completed.");
    }
}
