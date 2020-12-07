package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
import com.paf.exercise.exercise.dto.ctrader.Position;
import com.paf.exercise.exercise.util.MessageAction;
import com.paf.exercise.exercise.util.Symbol;

import java.util.Arrays;

public class ForexLyticMessageParser extends MessageParser {

    @Override
    public MessageAction getMessageAction(String message) throws IllegalArgumentException {
        if (message.startsWith("CLOSE")) {
            return MessageAction.CLOSE;

        } else if (isTradeMessage(message)) {
            return MessageAction.OPEN;
        } else {
            throw new IllegalArgumentException(message + " has not a valid action");
        }
    }

    @Override
    public boolean isTradeMessage(String message) {
        String symbolName = message.split("\\s+|-|_")[0].trim();

        return Arrays.stream(Symbol.values()).anyMatch(symbol -> symbol.name().equalsIgnoreCase(symbolName));
    }

    @Override
    public OpenPositionRequest onOpenPosition(String channelName, String message, Long messageId) {
        System.out.println("opening: " + message + ", channelName: " + channelName);

        Double entry = extractFirstDecimal(message);

        Double tp = null;
        int index = message.indexOf("PROFIT");
        if (index != -1) {
            tp = extractFirstDecimal(message.substring(index));
        }

        Double sl = null;
        index = message.indexOf("LOSS");
        if (index != -1) {
            sl = extractFirstDecimal(message.substring(index));
        }

        Position position = new Position(getSymbol(message), getTradeType(message), 0.01, normalizeLabel(message + channelName), channelName, entry, sl, tp);
        return new OpenPositionRequest(position, messageId);
    }

    @Override
    public ClosePositionRequest onClosePosition(String channelName, String message, Long messageId) {
        System.out.println("closing: " + message + ", channelName: " + channelName);

        return new ClosePositionRequest(messageId, normalizeLabel(message + channelName));
    }
}
