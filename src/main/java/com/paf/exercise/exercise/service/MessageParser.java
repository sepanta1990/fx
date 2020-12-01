package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
import com.paf.exercise.exercise.util.MessageAction;

public interface MessageParser {
    MessageAction getMessageAction(String message) throws IllegalArgumentException;

    boolean isTradeMessage(String message);

    OpenPositionRequest onOpenPosition(Long chatId, String message, Long messageId);

    ClosePositionRequest onClosePosition(Long chatId, String message, Long messageId);
}
