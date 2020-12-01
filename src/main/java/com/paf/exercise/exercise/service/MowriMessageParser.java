package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
import com.paf.exercise.exercise.util.MessageAction;

public class MowriMessageParser implements MessageParser {

    @Override
    public MessageAction getMessageAction(String message) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean isTradeMessage(String message) {
        return false;
    }

    @Override
    public OpenPositionRequest onOpenPosition(Long chatId, String message, Long messageId) {
        return null;
    }

    @Override
    public ClosePositionRequest onClosePosition(Long chatId, String message, Long messageId) {
        return null;
    }
}
