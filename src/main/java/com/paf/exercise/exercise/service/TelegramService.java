package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
import com.paf.exercise.exercise.dto.ctrader.Position;
import com.paf.exercise.exercise.dto.ctrader.PositionResponse;
import com.paf.exercise.exercise.dto.telegram.SendMessageRequest;
import com.paf.exercise.exercise.dto.telegram.Update;
import com.paf.exercise.exercise.util.MessageAction;
import com.paf.exercise.exercise.util.Symbol;
import com.paf.exercise.exercise.util.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class TelegramService {

    private final LinkedBlockingQueue<OpenPositionRequest> openPositionQueue = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<ClosePositionRequest> closePositionQueue = new LinkedBlockingQueue<>();

    @Value("${telegram.bot.chat.id}")
    private String telegramBotChatId;

    @Autowired
    private TelegramClient telegramClient;

    private MessageAction previousMessageAction = null;

    public void positionResponse(PositionResponse positionResponse) {
        telegramClient.sendMessage(new SendMessageRequest(telegramBotChatId, positionResponse.getMessage(), positionResponse.getReplyToMessageId()));
    }

    public void getLatestOpenPositions(List<OpenPositionRequest> list) {
        openPositionQueue.drainTo(list);
    }

    public void getLatestClosePositions(List<ClosePositionRequest> list) {
        closePositionQueue.drainTo(list);
    }


    public void process(Update update) {
        Long messageId = null;
        try {

            if (update.getMessage().getDate() * 1000 < System.currentTimeMillis() - 30 * 60 * 1000) {
                return;
            }

            messageId = update.getMessage().getMessageId();

            MessageAction messageAction = getMessageAction(update.getMessage().getText());

            if (previousMessageAction == MessageAction.CLOSE) {
                if (messageAction == MessageAction.OPEN) {
                    onClosePosition(update.getMessage().getText(), messageId);

                }
            } else {
                if (messageAction == MessageAction.OPEN) {
                    onOpenPosition(update.getMessage().getText(), messageId);
                }
            }

            previousMessageAction = messageAction;
        } catch (Exception e) {
            e.printStackTrace();
            if (messageId != null) {
                telegramClient.sendMessage(new SendMessageRequest(telegramBotChatId, e.getMessage(), messageId));
            }
        }
    }


    private void onOpenPosition(String message, Long messageId) {
        message = message.trim().toUpperCase();

        System.out.println("opening: " + message);
        String[] str = message.split("\\s+");

        Double tp = null;
        if (str.length >= 8 && !str[9].equalsIgnoreCase("OPEN")) {
            tp = Double.parseDouble(str[9]);
        }

        Position position = new Position(Symbol.valueOf(str[0]), TradeType.valueOf(str[1]), 0.01, message, Double.parseDouble(str[3]), tp, Double.parseDouble(str[6]), true);
        OpenPositionRequest openPositionRequest = new OpenPositionRequest(position, messageId);

        openPositionQueue.add(openPositionRequest);

    }

    private void onClosePosition(String message, Long messageId) {
        message = message.trim().toUpperCase();

        System.out.println("closing: " + message);

        ClosePositionRequest closePositionRequest = new ClosePositionRequest(messageId, message);
        closePositionQueue.add(closePositionRequest);


    }

    private MessageAction getMessageAction(String message) throws IllegalArgumentException {
        message = message.trim().toUpperCase();

        if (message.startsWith("CLOSE")) {
            return MessageAction.CLOSE;

        } else if (isTradeMessage(message)) {
            return MessageAction.OPEN;
        } else {
            throw new IllegalArgumentException(message + " has not a valid action");
        }
    }

    private boolean isTradeMessage(String message) {
        String symbolName = message.split(" ")[0].trim();

        return Arrays.stream(Symbol.values()).anyMatch(symbol -> symbol.name().equalsIgnoreCase(symbolName));
    }
}
