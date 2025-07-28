package com.RabbitMQ_Kafka.Controller.rabbit;

import com.RabbitMQ_Kafka.Model.Wedding;
import com.RabbitMQ_Kafka.Service.rabbit.VenueService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("rabbit")
@RestController
@RequestMapping("/api/wedding/")
public class WeddingController {

    private final VenueService venueService;

    public WeddingController(VenueService venueService) {
        this.venueService = venueService;
    }


    @PostMapping
    public ResponseEntity<String> startWedding(@RequestBody Wedding wedding) {
        venueService.startWedding(wedding);
        return ResponseEntity.ok("Started Wedding!");
    }
}
