package com.paf.exercise.exercise.dto.ctrader;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class    PositionResponse {
    private Long replyToMessageId;
    private String message;
    private boolean ok;

    public PositionResponse() {
    }

    public PositionResponse(Long replyToMessageId, String message, boolean ok) {
        this.replyToMessageId = replyToMessageId;
        this.message = message;
        this.ok = ok;
    }

    public Long getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(Long replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "PositionResponse{" +
                "messageId=" + replyToMessageId +
                ", message='" + message + '\'' +
                ", ok=" + ok +
                '}';
    }
}
