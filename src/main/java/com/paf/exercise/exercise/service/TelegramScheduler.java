package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.BaseResponse;
import com.paf.exercise.exercise.dto.Update;
import com.paf.exercise.exercise.entity.Position;
import com.paf.exercise.exercise.util.MessageAction;
import com.paf.exercise.exercise.util.Symbol;
import com.paf.exercise.exercise.util.TradeType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class TelegramScheduler {

    private RestTemplate restTemplate;

    @Value("${telegram.base.url}")
    private String telegramBaseUrl;


    private MessageAction previousMessageAction = null;


    private long offset = 978065120;

    @PostConstruct
    public void init() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        builder.setConnectTimeout(10000);
        builder.setReadTimeout(30000);

        restTemplate = builder.build();
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 20000)
    public void fetchTelegramMessages() {


        List<Update> updateList = restTemplate.exchange(telegramBaseUrl + "/getUpdates?limit=1&offset=" + offset,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<BaseResponse<Update>>() {
                }).getBody().getResult();

        if (updateList.isEmpty()) {
            return;
        }


        try {


            Update update = updateList.get(0);
            offset = update.getUpdateId() + 1;

            MessageAction messageAction = getMessageAction(update.getMessage().getText());

            if (previousMessageAction == MessageAction.CLOSE) {
                if (messageAction == MessageAction.OPEN) {
                    parseMessage(MessageAction.CLOSE, update.getMessage().getText());

                }
            } else {
                if (messageAction == MessageAction.OPEN) {
                    parseMessage(messageAction, update.getMessage().getText());
                }
            }

            previousMessageAction = messageAction;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void parseMessage(MessageAction messageAction, String message) throws IllegalArgumentException {
        message = message.trim().toUpperCase();

        if (messageAction == MessageAction.CLOSE) {
            System.out.println("closing: " + message);

        } else if (messageAction == MessageAction.OPEN) {
            System.out.println("opening: " + message);
            String[] str = message.split("\\s+");


            Double tp = null;
            if (str.length >= 8 && !str[9].equalsIgnoreCase("OPEN")) {
                tp = Double.parseDouble(str[9]);
            }

            Position position = new Position(Symbol.valueOf(str[0]), TradeType.valueOf(str[1]), 0.01, message, Double.parseDouble(str[3]), tp, Double.parseDouble(str[6]), true);
        }
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
