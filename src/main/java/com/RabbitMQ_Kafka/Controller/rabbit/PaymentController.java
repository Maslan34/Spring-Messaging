package com.RabbitMQ_Kafka.Controller.rabbit;

import com.RabbitMQ_Kafka.Model.Order;
import com.RabbitMQ_Kafka.Service.rabbit.PaymentService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("rabbit")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        paymentService.createOrder(order);
        return ResponseEntity.ok("Order Received!\n");
    }
}
