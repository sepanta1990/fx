package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
import com.paf.exercise.exercise.dto.ctrader.Position;
import com.paf.exercise.exercise.util.MessageAction;
import com.paf.exercise.exercise.util.Symbol;
import com.paf.exercise.exercise.util.TradeType;

import java.util.Arrays;

public class MowriMessageParser implements MessageParser {

    @Override
    public MessageAction getMessageAction(String message) throws IllegalArgumentException {
        if (message.startsWith("CLOSE")) {
            return MessageAction.CLOSE;

        }
        if (isTradeMessage(message)) {
            return MessageAction.OPEN;
        }
        throw new IllegalArgumentException(message + " has not a valid action");

    }

    @Override
    public boolean isTradeMessage(String message) {
        String symbolName = message.split("-")[0].trim();

        return Arrays.stream(Symbol.values()).anyMatch(symbol -> symbol.name().equalsIgnoreCase(symbolName));
    }

    @Override
    public OpenPositionRequest onOpenPosition(Long chatId, String message, Long messageId) {
        System.out.println("opening: " + message + ", chatId: " + chatId);

        Double entry = extractFirstDecimal(message);

        Double tp = null;
        int index = message.indexOf("TP");
        if (index != -1) {
            tp = extractFirstDecimal(message.substring(index));
        }

        Double sl = null;
        index = message.indexOf("SL");
        if (index != -1) {
            sl = extractFirstDecimal(message.substring(index));
        }

        String[] str = message.split("\\s+|-|_");

        Position position = new Position(Symbol.valueOf(str[0]), TradeType.valueOf(str[1]), 0.01, message + chatId, chatId.toString(), entry, tp, sl, true);
        return new OpenPositionRequest(position, messageId);
    }

    @Override
    public ClosePositionRequest onClosePosition(Long chatId, String message, Long messageId) {
        System.out.println("closing: " + message + ", chatId: " + chatId);

        return new ClosePositionRequest(messageId, message + chatId);
    }
}
