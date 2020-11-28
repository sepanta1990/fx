package com.paf.exercise.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paf.exercise.exercise.util.MessageAction;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenPositionRequest extends  PositionRequest{
    private Position position;

    public OpenPositionRequest() {
    }

    public OpenPositionRequest(Position position, Long messageId) {
        super(MessageAction.OPEN, messageId);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
