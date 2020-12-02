package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
import com.paf.exercise.exercise.dto.ctrader.PositionResponse;
import com.paf.exercise.exercise.dto.telegram.Chat;
import com.paf.exercise.exercise.dto.telegram.SendMessageRequest;
import com.paf.exercise.exercise.dto.telegram.Update;
import com.paf.exercise.exercise.util.MessageAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

            Chat chat = update.getMessage().getChat();
            if (chat == null) {
                throw new Exception("Message is not forwarded from a channel");
            }


            MessageParser messageParser = getMessageParser(chat.getId());

            MessageAction messageAction = messageParser.getMessageAction(update.getMessage().getText());

            if (previousMessageAction == MessageAction.CLOSE) {
                if (messageAction == MessageAction.OPEN) {
                    closePositionQueue.add(messageParser.onClosePosition(chat.getId(), update.getMessage().getText(), messageId));
                }
            } else {
                if (messageAction == MessageAction.OPEN) {
                    openPositionQueue.add(messageParser.onOpenPosition(chat.getId(), update.getMessage().getText(), messageId));
                }
            }
            previousMessageAction = messageAction;
        } catch (Exception e) {
            e.printStackTrace();
            if (messageId != null) {
                telegramClient.sendMessage(new SendMessageRequest(telegramBotChatId, "EXCEPTION: "+ e.getMessage(), messageId));
            }
        }
    }

    private MessageParser getMessageParser(Long id) {
        if (id == -1001305938901L) {
            return new ForexLyticMessageParser();
        } else if (id == -1001390026460L) {
            return new MowriMessageParser();
        }
        throw new IllegalArgumentException("Channel is not supported with id: " + id);
    }

}
