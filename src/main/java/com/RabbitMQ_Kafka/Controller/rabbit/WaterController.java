package com.RabbitMQ_Kafka.Controller.rabbit;

import com.RabbitMQ_Kafka.Service.rabbit.WaterService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("rabbit")
@RestController
@RequestMapping("/api/water")
public class WaterController {


    private final WaterService waterService;

    public WaterController(WaterService waterService) {
        this.waterService = waterService;
    }

    @PostMapping
    public void sendWater(@RequestParam String faucet, @RequestParam int liter) {
        waterService.start(faucet, liter);
    }
}



