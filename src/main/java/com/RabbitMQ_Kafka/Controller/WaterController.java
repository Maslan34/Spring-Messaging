package com.RabbitMQ_Kafka.Controller;

import com.RabbitMQ_Kafka.Service.WaterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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



