package com.RabbitMQ_Kafka.Model;

public class Ticket {
    private String userId;
    private String busId;
    private String seatNo;
    private int attempt;

    public Ticket() {
    }

    public Ticket(String userId, String busId, String seatNo, int attempt) {
        this.userId = userId;
        this.busId = busId;
        this.seatNo = seatNo;
        this.attempt = attempt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "userId='" + userId + '\'' +
                ", busId='" + busId + '\'' +
                ", seatNo='" + seatNo + '\'' +
                ", attempt=" + attempt +
                '}';
    }
}




