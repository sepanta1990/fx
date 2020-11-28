package com.paf.exercise.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paf.exercise.exercise.util.MessageAction;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClosePositionRequest extends PositionRequest {
    private String label;

    public ClosePositionRequest() {
    }

    public ClosePositionRequest(Long messageId, String label) {
        super(MessageAction.CLOSE, messageId);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
