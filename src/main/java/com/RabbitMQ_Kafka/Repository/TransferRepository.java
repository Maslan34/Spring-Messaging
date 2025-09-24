package com.RabbitMQ_Kafka.Repository;


import com.RabbitMQ_Kafka.Model.FootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<FootballPlayer, Long> {
}
