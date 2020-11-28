package com.paf.exercise.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paf.exercise.exercise.util.MessageAction;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionRequest {
    private MessageAction messageAction;
    private Long messageId;


    public PositionRequest() {
    }

    public PositionRequest(MessageAction messageAction, Long messageId) {
        this.messageAction = messageAction;
        this.messageId = messageId;
    }

    public MessageAction getMessageAction() {
        return messageAction;
    }

    public void setMessageAction(MessageAction messageAction) {
        this.messageAction = messageAction;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
