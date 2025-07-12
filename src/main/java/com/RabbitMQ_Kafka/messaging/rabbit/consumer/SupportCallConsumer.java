package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Call;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SupportCallConsumer {

    @RabbitListener(queues = "${sample.rabbitmq.priority.queue}", containerFactory = "priority")
    public void callConsume(Call call, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, InterruptedException {
        System.out.println("Handled Message: " + call.getMessage());
        // Thread Sleep has been added to ensure that incoming messages are queued.
        // If this is not done, the incoming message will be consumed directly
        // and the priority process will not be performed.
        // This section was added because threads are very fast.
        // Please remove this line and observe the event again.
        Thread.sleep(1000);
        channel.basicAck(tag, false);


    }

}
