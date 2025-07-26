package com.RabbitMQ_Kafka.Model;

public class Wedding {
    String id="";
    Boolean isVenueSuccess;
    Boolean isCateringSuccess;
    Boolean isEntertainmentSuccess;
    Boolean isInvitationSuccess;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getVenueSuccess() {
        return isVenueSuccess;
    }

    public void setVenueSuccess(Boolean venueSuccess) {
        isVenueSuccess = venueSuccess;
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
        return "Wedding{" +
                "id='" + id + '\'' +
                ", isVenueSuccess=" + isVenueSuccess +
                ", isCateringSuccess=" + isCateringSuccess +
                ", isEntertainmentSuccess=" + isEntertainmentSuccess +
                ", isInvitationSuccess=" + isInvitationSuccess +
                '}';
    }
}
