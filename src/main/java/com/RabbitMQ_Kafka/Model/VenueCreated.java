package com.RabbitMQ_Kafka.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class VenueCreated implements Serializable {

    private String weddingId;
    private String venueName;
    private LocalDate reservationDate;
    Boolean isCateringSuccess;
    Boolean isEntertainmentSuccess;
    Boolean isInvitationSuccess;

    //required for RabbitMQ deserialization
    public VenueCreated() {
    }


    public VenueCreated(String weddingId, String venueName, LocalDate reservationDate, Boolean isCateringSuccess, Boolean isEntertainmentSuccess, Boolean isInvitationSuccess) {
        this.weddingId = weddingId;
        this.venueName = venueName;
        this.reservationDate = reservationDate;
        this.isCateringSuccess = isCateringSuccess;
        this.isEntertainmentSuccess = isEntertainmentSuccess;
        this.isInvitationSuccess = isInvitationSuccess;
    }

    public String getWeddingId() {
        return weddingId;
    }

    public void setWeddingId(String weddingId) {
        this.weddingId = weddingId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Boolean getCateringSuccess() {
        return isCateringSuccess;
    }

    public void setCateringSuccess(Boolean cateringSuccess) {
        isCateringSuccess = cateringSuccess;
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
        return "VenueReservedEvent{" +
                "weddingId='" + weddingId + '\'' +
                ", venueName='" + venueName + '\'' +
                ", reservationDate=" + reservationDate +
                '}';
    }
}
