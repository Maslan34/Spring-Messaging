package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Order;
import com.RabbitMQ_Kafka.messaging.rabbit.producer.ReceiptProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Profile("rabbit")
@Component
public class ShippingConsumer {

    private final ReceiptProducer receiptProducer;
    public ShippingConsumer(ReceiptProducer receiptProducer) {
        this.receiptProducer = receiptProducer;
    }
    @RabbitListener(queues = "${sample.rabbitmq.shipping.queue}")
    public void handleShipping(Order order) {
        System.out.println("Shipping message received: " + order.toString());
        System.out.println("Sending Order to Receipt Producer in Shipping Consumer\n");
        receiptProducer.sendReceipt(order);
    }
}
