package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Order;
import com.RabbitMQ_Kafka.messaging.rabbit.producer.ShippingProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Profile("rabbit")
@Component
public class PaymentConsumer {


    private final ShippingProducer shippingProducer;

    public PaymentConsumer(ShippingProducer shippingProducer) {
        this.shippingProducer = shippingProducer;
    }

    @RabbitListener(queues = "${sample.rabbitmq.payment.queue}")
    public void handlePayment(Order order) {
        System.out.println("Payment message received: " + order.toString());
        System.out.println("Sending Order to Shipping Producer in Payment Consumer\n");
        shippingProducer.sendShipping(order);
    }


}

