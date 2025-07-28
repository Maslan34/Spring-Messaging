package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Tea;
import com.RabbitMQ_Kafka.messaging.rabbit.producer.TeaProducer;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Profile("rabbit")
@Component
public class TeaConsumer {


    private final TeaProducer teaProducer;

    public TeaConsumer(TeaProducer teaProducer) {
        this.teaProducer = teaProducer;
    }

    @RabbitListener(queues = "${sample.rabbitmq.tea.queue}", containerFactory = "manualAck")
    public void handleMessage(@Payload Tea tea,
                              Channel channel,
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            if (tea.getElapsedTime() > 45) {
                System.out.println("Tea Brewed. Duration: " + tea.getElapsedTime());
                channel.basicAck(deliveryTag, false);
            } else {
                System.out.println("Tea was not brewed long enough. Duration: " + tea.getElapsedTime());
                System.out.println("Duration is increased by 0.1.");
                // Here, the 3rd parameter -> is set to false and the requeue feature is disabled
                // We put this tea object in the queue and then publish the current tea object to rabbit.

                // 2nd parameter -> Rejects only the message with this deliveryTag.
                // When true, rejects all messages up to this deliveryTag (including previous ones). (mass reject)

                channel.basicNack(deliveryTag, false, false);
                tea.setElapsedTime(tea.getElapsedTime() + 0.1);


                 /*
                    // In the loop scenario, when the system is restarted,
                    // this case will attempt to consume the message again, even though it was previously nacked.
                    channel.basicNack(deliveryTag, false, true);

                  */

                // Republish the updated message
                teaProducer.brewTea(tea);
            }
        } catch (Exception e) {
            System.err.println("Tea was not brewed: " + e.getMessage());
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ioEx) {
                System.err.println("Error Sending Nack: " + ioEx.getMessage());
            }
        }
    }
}
