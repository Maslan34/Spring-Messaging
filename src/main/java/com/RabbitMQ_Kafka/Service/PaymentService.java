package com.RabbitMQ_Kafka.Service;


import com.RabbitMQ_Kafka.Model.Order;
import com.RabbitMQ_Kafka.messaging.rabbit.producer.PaymentProducer;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentProducer paymentProducer;

    public PaymentService(PaymentProducer orderProducer) {
        this.paymentProducer = orderProducer;
    }

    public void createOrder(Order order) {
        System.out.println("Order Created in PaymentService");
        paymentProducer.sendOrder(order);

    }

}
