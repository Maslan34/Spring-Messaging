package com.RabbitMQ_Kafka.Model;

public class Baggage {
    private String baggageId;
    private String passengerName;
    private double weightKg;
    private long timestamp;

    public Baggage() {

    }

    public Baggage(String baggageId, String passengerName, double weightKg, long timestamp) {
        this.baggageId = baggageId;
        this.passengerName = passengerName;
        this.weightKg = weightKg;
        this.timestamp = timestamp;
    }

    public String getBaggageId() {
        return baggageId;
    }

    public void setBaggageId(String baggageId) {
        this.baggageId = baggageId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
