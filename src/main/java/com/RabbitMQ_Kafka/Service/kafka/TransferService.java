package com.RabbitMQ_Kafka.Service.kafka;


import com.RabbitMQ_Kafka.Model.FootballPlayer;
import com.RabbitMQ_Kafka.Model.OutboxEvent;
import com.RabbitMQ_Kafka.Repository.OutboxRepository;
import com.RabbitMQ_Kafka.Repository.TransferRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final OutboxRepository outboxRepo;
    private final ObjectMapper mapper = new ObjectMapper();


    public TransferService(TransferRepository transferRepository, OutboxRepository outboxRepo) {
        this.transferRepository = transferRepository;
        this.outboxRepo = outboxRepo;
    }

    @Transactional("transactionManager")
    public FootballPlayer transferPlayer(String player, String from, String to, BigDecimal fee) throws JsonProcessingException {
        // 1- Write Main Table
        FootballPlayer transfer = new FootballPlayer();
        transfer.setPlayerName(player);
        transfer.setFromTeam(from);
        transfer.setToTeam(to);
        transfer.setFee(fee);
        transferRepository.save(transfer);

        // 2- Create Outbox Event
        OutboxEvent event = new OutboxEvent();
        event.setAggregateType("PlayerTransfer");
        event.setAggregateId(transfer.getId());
        event.setEventType("PlayerTransferred");



        String payload = mapper.writeValueAsString(transfer); // Converting Transfer Object To String
        event.setPayload(payload);

        System.out.println(payload);

        outboxRepo.save(event);

        return transfer;
    }
}
