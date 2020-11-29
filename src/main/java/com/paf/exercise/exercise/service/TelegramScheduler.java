package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.telegram.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramScheduler {

    @Autowired
    private TelegramClient telegramClient;

    @Autowired
    private TelegramService telegramService;

    private long offset = 978065120;

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void fetchTelegramMessages() {

        List<Update> updateList = telegramClient.getUpdates(offset, 100);
        if (updateList.isEmpty()) {
            return;
        }

        for (Update update : updateList) {
            offset = update.getUpdateId() + 1;
            telegramService.process(update);
        }

    }
}
