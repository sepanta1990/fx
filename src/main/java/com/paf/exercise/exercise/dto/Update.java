package com.paf.exercise.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Update {
    @JsonProperty("update_id")
    private Integer updateId;
    @JsonProperty("message")
    private Message message;

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Update{" +
                "updateId=" + updateId +
                ", message=" + message +
                '}';
    }
}