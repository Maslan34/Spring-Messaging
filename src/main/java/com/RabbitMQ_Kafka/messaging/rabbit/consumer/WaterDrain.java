package com.RabbitMQ_Kafka.messaging.rabbit.consumer;

import com.RabbitMQ_Kafka.Model.Water;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WaterDrain {


    @Component
    public static class WaterDrainerA {

        @RabbitListener(queues = "${sample.rabbitmq.loadbalancing.queue}")
        public void drain(Water msg) throws InterruptedException {
            System.out.println("[A] Drainer extracted " + msg.liter+ " liters water.");
            Thread.sleep(1000); // water extraction time
        }
    }

    @Component
    public static class WaterDrainerB {

        @RabbitListener(queues = "${sample.rabbitmq.loadbalancing.queue}")
        public void drain(Water msg) throws InterruptedException {
            System.out.println("[B] Drainer extracted " + msg.liter+ " liters water.");
            Thread.sleep(1000); // water extraction time
        }
    }



    /*
    // Alternatively, this can be designed in a single function, allowing the number of threads to be adjusted dynamically.

    @RabbitListener(
            queues = "${sample.rabbitmq.loadbalancing.queue}",
            concurrency = "2-4"  //Between 2 and 4 dynamic threads, which can increase or decrease depending on the workload.
    )
    public void drain(Water msg) throws InterruptedException {
        System.out.println("[Thread-" + Thread.currentThread().getId() + "] Extracted: " + msg.liter+" liter water.");
        Thread.sleep(1000);
    }

     */
}
