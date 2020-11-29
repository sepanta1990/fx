package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.*;
import com.paf.exercise.exercise.util.MessageAction;
import com.paf.exercise.exercise.util.Symbol;
import com.paf.exercise.exercise.util.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class TelegramScheduler {

    @Autowired
    private TelegramClient telegramClient;

    @Value("${telegram.bot.chat.id}")
    private String telegramBotChatId;

    private final LinkedBlockingQueue<PositionRequest> queue = new LinkedBlockingQueue<>();


    private MessageAction previousMessageAction = null;

    private long offset = 978065120;

    public void getLatestRequests(List<PositionRequest> list) {
        queue.drainTo(list);
    }


    @Scheduled(initialDelay = 10000, fixedDelay = 20000)
    public void fetchTelegramMessages() {

        List<Update> updateList = telegramClient.getUpdates(offset, 100);
        if (updateList.isEmpty()) {
            return;
        }

        for (Update update : updateList) {
            process(update);
        }

    }

    private void process(Update update) {
        Long messageId = null;
        try {

            offset = update.getUpdateId() + 1;
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

        queue.add(openPositionRequest);

    }

    private void onClosePosition(String message, Long messageId) {
        message = message.trim().toUpperCase();

        System.out.println("closing: " + message);

        ClosePositionRequest closePositionRequest = new ClosePositionRequest(messageId, message);
        queue.add(closePositionRequest);


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
