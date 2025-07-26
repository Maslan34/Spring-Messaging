package com.RabbitMQ_Kafka.Model;

import java.io.Serializable;

public class CateringCreated implements Serializable {

    private String weddingId;
    private String cateringCompany;
    Boolean isEntertainmentSuccess;
    Boolean isInvitationSuccess;

    //required for RabbitMQ deserialization
    public CateringCreated() {
    }


    public CateringCreated(String weddingId, String cateringCompany, Boolean isEntertainmentSuccess, Boolean isInvitationSuccess) {
        this.weddingId = weddingId;
        this.cateringCompany = cateringCompany;
        this.isEntertainmentSuccess = isEntertainmentSuccess;
        this.isInvitationSuccess = isInvitationSuccess;
    }

    public String getWeddingId() {
        return weddingId;
    }

    public void setWeddingId(String weddingId) {
        this.weddingId = weddingId;
    }

    public String getCateringCompany() {
        return cateringCompany;
    }

    public void setCateringCompany(String cateringCompany) {
        this.cateringCompany = cateringCompany;
    }

    public Boolean getEntertainmentSuccess() {
        return isEntertainmentSuccess;
    }

    public void setEntertainmentSuccess(Boolean entertainmentSuccess) {
        isEntertainmentSuccess = entertainmentSuccess;
    }

    public Boolean getInvitationSuccess() {
        return isInvitationSuccess;
    }

    public void setInvitationSuccess(Boolean invitationSuccess) {
        isInvitationSuccess = invitationSuccess;
    }

    @Override
    public String toString() {
        return "CateringReservedEvent{" +
                "weddingId='" + weddingId + '\'' +
                ", cateringCompany='" + cateringCompany + '\'' +
                '}';
    }
}

