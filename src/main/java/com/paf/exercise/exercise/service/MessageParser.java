package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
import com.paf.exercise.exercise.util.MessageAction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface MessageParser {
    MessageAction getMessageAction(String message) throws IllegalArgumentException;

    boolean isTradeMessage(String message);

    OpenPositionRequest onOpenPosition(Long chatId, String message, Long messageId);

    ClosePositionRequest onClosePosition(Long chatId, String message, Long messageId);

    default Double extractFirstDecimal(String str) {
        if (str == null)
            return null;
        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+([0-9]+)?.");
        Matcher matcher = pattern.matcher(str);

        boolean matchFound = matcher.find();
        if (matchFound) {
            return Double.parseDouble(matcher.group(0));
        } else {
            return null;
        }
    }
}
