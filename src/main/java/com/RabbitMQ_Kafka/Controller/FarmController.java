package com.RabbitMQ_Kafka.Controller;

import com.RabbitMQ_Kafka.Model.Forage;
import com.RabbitMQ_Kafka.Service.FarmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/farm")

public class FarmController {
    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @PostMapping
    public ResponseEntity<String> startFeeding(@RequestBody Forage forage) {
        farmService.feed(forage);
        return ResponseEntity.ok("Completed Feeding All Animals!");
    }
}
