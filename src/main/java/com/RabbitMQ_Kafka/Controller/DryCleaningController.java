package com.RabbitMQ_Kafka.Controller;

import com.RabbitMQ_Kafka.Model.Dress;
import com.RabbitMQ_Kafka.Service.DressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
