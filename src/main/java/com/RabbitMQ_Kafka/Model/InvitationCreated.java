package com.RabbitMQ_Kafka.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InvitationCreated implements Serializable {

    private String weddingId;
    private String recipientGroup;
    private LocalDateTime sentAt;
    private boolean success;

    //required for RabbitMQ deserialization
    public InvitationCreated() {
    }

    public InvitationCreated(String weddingId, String recipientGroup, LocalDateTime sentAt, boolean success) {
        this.weddingId = weddingId;
        this.recipientGroup = recipientGroup;
        this.sentAt = sentAt;
        this.success = success;
    }

    public String getWeddingId() {
        return weddingId;
    }

    public void setWeddingId(String weddingId) {
        this.weddingId = weddingId;
    }

    public String getRecipientGroup() {
        return recipientGroup;
    }

    public void setRecipientGroup(String recipientGroup) {
        this.recipientGroup = recipientGroup;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "InvitationCreated{" +
                "weddingId='" + weddingId + '\'' +
                ", recipientGroup='" + recipientGroup + '\'' +
                ", sentAt=" + sentAt +
                ", success=" + success +
                '}';
    }
}
