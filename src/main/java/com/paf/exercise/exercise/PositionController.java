package com.paf.exercise.exercise;

import com.paf.exercise.exercise.dto.PositionRequest;
import com.paf.exercise.exercise.service.TelegramScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("/positions")
public class PositionController {

    @Autowired
    private TelegramScheduler telegramScheduler;

    @GetMapping
    public List<PositionRequest> getLatestPositions() {
        List<PositionRequest> list = new ArrayList<>();
        telegramScheduler.getLatestRequests(list);

        return list;
    }
}
