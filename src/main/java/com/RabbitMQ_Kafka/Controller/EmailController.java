package com.RabbitMQ_Kafka.Controller;

import com.RabbitMQ_Kafka.Model.Email;
import com.RabbitMQ_Kafka.Service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> checkEmail(@RequestBody Email email) {
        return emailService.checkMail(email);
    }
}
