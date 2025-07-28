package com.RabbitMQ_Kafka.Controller.rabbit;

import com.RabbitMQ_Kafka.Model.Dress;
import com.RabbitMQ_Kafka.Service.rabbit.DressService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("rabbit")
@RestController
@RequestMapping("/api/dry/")
public class DryCleaningController {

    private final DressService dressService;

    public DryCleaningController(DressService dressService){
        this.dressService = dressService;
    }

    @PostMapping
    public ResponseEntity<String> seperateDress(@RequestBody Dress dress){
        dressService.seperate(dress);
        return ResponseEntity.ok("Dress Seperated!");
    }

}
