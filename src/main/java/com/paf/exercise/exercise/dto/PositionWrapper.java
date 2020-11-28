package com.paf.exercise.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paf.exercise.exercise.util.MessageAction;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionWrapper {
    private Position position;
    private Long messageId;
    private MessageAction messageAction;

    public PositionWrapper() {
    }

    public PositionWrapper(Position position, Long messageId, MessageAction messageAction) {
        this.position = position;
        this.messageId = messageId;
        this.messageAction = messageAction;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public MessageAction getMessageAction() {
        return messageAction;
    }

    public void setMessageAction(MessageAction messageAction) {
        this.messageAction = messageAction;
    }
}
