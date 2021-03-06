package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
import com.paf.exercise.exercise.util.MessageAction;
import com.paf.exercise.exercise.util.Symbol;
import com.paf.exercise.exercise.util.TradeType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MessageParser {
    abstract MessageAction getMessageAction(String message) throws IllegalArgumentException;

    abstract boolean isTradeMessage(String message);

    abstract OpenPositionRequest onOpenPosition(String channelName, String message, Long messageId);

    abstract ClosePositionRequest onClosePosition(String channelName, String message, Long messageId);

    Double extractFirstDecimal(String str) {
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

    String normalizeLabel(String label) {
        return label.replaceAll("[^a-zA-Z0-9@.:-]", "");
    }

    Symbol getSymbol(String message) throws IllegalArgumentException {
        for (Symbol symbol : Symbol.values()) {
            if (message.contains(symbol.name())) {
                return symbol;
            }
        }
        throw new IllegalArgumentException("Valid symbol not found in: " + message);
    }

    TradeType getTradeType(String message) {
        if (message.contains("SELL")) {
            return TradeType.SELL;
        } else if (message.contains("BUY")) {
            return TradeType.BUY;
        }
        throw new IllegalArgumentException("No valid trade type in: " + message);
    }
}
