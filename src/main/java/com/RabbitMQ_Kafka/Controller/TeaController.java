package com.RabbitMQ_Kafka.Controller;

import com.RabbitMQ_Kafka.Model.Tea;
import com.RabbitMQ_Kafka.Service.TeaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tea")
public class TeaController {

    private final TeaService teaService;

    public TeaController(TeaService teaService){
        this.teaService = teaService;
    }

    @PostMapping
    public ResponseEntity<Boolean> brewTea(@RequestBody Tea tea){
        return teaService.brew(tea);
    }
}
