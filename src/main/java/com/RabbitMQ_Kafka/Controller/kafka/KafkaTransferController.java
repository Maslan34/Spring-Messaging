package com.RabbitMQ_Kafka.Controller.kafka;

import com.RabbitMQ_Kafka.Model.FootballPlayer;
import com.RabbitMQ_Kafka.Service.kafka.TransferService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/kafka/transfers")
@Profile("kafka")
public class KafkaTransferController {

    private final TransferService transferService;

    public KafkaTransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<FootballPlayer> createTransfer(@RequestBody TransferRequest request) {

        FootballPlayer player = new FootballPlayer();
        try {
            player = transferService.transferPlayer(
                    request.getPlayerName(),
                    request.getFromTeam(),
                    request.getToTeam(),
                    request.getFee()
            );
        } catch (Exception e) {
            System.out.println("Mapper SerDe Fail!");
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok(player);
    }


    // This is an important point! If this class is not static,
    // the consumer (I'm using Spring Jackson by default) won't be able to access it.
    @Getter
    @Setter
    static public class TransferRequest {

        public TransferRequest() {
        } // Required for Jackson. Without this, it cannot be instantiated.

        private String playerName;
        private String fromTeam;
        private String toTeam;
        private BigDecimal fee;


    }
}
