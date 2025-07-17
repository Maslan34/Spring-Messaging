package com.RabbitMQ_Kafka.Model;

public class Water {

    public String faucetName;
    public int liter;

    public Water(String faucetName, int liter) {
        this.faucetName = faucetName;
        this.liter = liter;
    }

    @Override
    public String toString() {
        return "Faucet " + faucetName + " pumped " + liter + " liters of water.";
    }


}
