package com.RabbitMQ_Kafka.Model;
import java.io.Serializable;

public class EntertainmentCreated implements Serializable {

    private String weddingId;
    private String entertainmentCompany;
    private boolean isInvitationSuccess;

    //required for RabbitMQ deserialization
    public EntertainmentCreated() {
    }

    public EntertainmentCreated(String weddingId, String entertainmentCompany, boolean isInvitationSuccess) {
        this.weddingId = weddingId;
        this.entertainmentCompany = entertainmentCompany;
        this.isInvitationSuccess = isInvitationSuccess;
    }

    public String getWeddingId() {
        return weddingId;
    }

    public void setWeddingId(String weddingId) {
        this.weddingId = weddingId;
    }

    public String getEntertainmentCompany() {
        return entertainmentCompany;
    }

    public void setEntertainmentCompany(String entertainmentCompany) {
        this.entertainmentCompany = entertainmentCompany;
    }

    public boolean isInvitationSuccess() {
        return isInvitationSuccess;
    }

    public void setInvitationSuccess(boolean invitationSuccess) {
        isInvitationSuccess = invitationSuccess;
    }

    @Override
    public String toString() {
        return "EntertainmentCreated{" +
                "weddingId='" + weddingId + '\'' +
                ", entertainmentCompany='" + entertainmentCompany + '\'' +
                ", isInvitationSuccess=" + isInvitationSuccess +
                '}';
    }
}
