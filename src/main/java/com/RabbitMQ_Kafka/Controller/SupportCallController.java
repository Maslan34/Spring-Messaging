package com.RabbitMQ_Kafka.Controller;

import com.RabbitMQ_Kafka.Model.Call;
import com.RabbitMQ_Kafka.Service.SupportCallService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/support")
public class SupportCallController {

    private final SupportCallService supportCallService;

    public SupportCallController(SupportCallService supportCallService) {
        this.supportCallService = supportCallService;
    }

    @PostMapping
    public void call(@RequestBody Call call) {
        supportCallService.call(call);
    }
}
