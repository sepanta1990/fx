package com.paf.exercise.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("message_id")
    private Integer messageId;
    @JsonProperty("text")
    private String text;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", text='" + text + '\'' +
                '}';
    }
}
