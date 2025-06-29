package com.RabbitMQ_Kafka.Model;

import com.RabbitMQ_Kafka.Model.enums.Colors;
import lombok.Data;

@Data
public class Dress {

    Colors color;
    Double width;
    Double height;
    Boolean isDelicate;



}
