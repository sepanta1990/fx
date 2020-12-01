package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
import com.paf.exercise.exercise.dto.ctrader.Position;
import com.paf.exercise.exercise.util.MessageAction;
import com.paf.exercise.exercise.util.Symbol;
import com.paf.exercise.exercise.util.TradeType;

import java.util.Arrays;

public class ForexLyticMessageParser implements MessageParser {

    @Override
    public MessageAction getMessageAction(String message) throws IllegalArgumentException {
        message = message.trim().toUpperCase();

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
        String symbolName = message.split(" ")[0].trim();

        return Arrays.stream(Symbol.values()).anyMatch(symbol -> symbol.name().equalsIgnoreCase(symbolName));
    }

    @Override
    public OpenPositionRequest onOpenPosition(Long chatId, String message, Long messageId) {
        message = message.trim().toUpperCase();

        System.out.println("opening: " + message);
        String[] str = message.split("\\s+");

        Double tp = null;
        if (str.length >= 8 && !str[9].equalsIgnoreCase("OPEN")) {
            tp = Double.parseDouble(str[9]);
        }

        Position position = new Position(Symbol.valueOf(str[0]), TradeType.valueOf(str[1]), 0.01, message + chatId, chatId.toString(), Double.parseDouble(str[3]), tp, Double.parseDouble(str[6]), true);
        return new OpenPositionRequest(position, messageId);
    }

    @Override
    public ClosePositionRequest onClosePosition(Long chatId, String message, Long messageId) {
        message = message.trim().toUpperCase();

        System.out.println("closing: " + message);

        return new ClosePositionRequest(messageId, message + chatId);
    }
}
