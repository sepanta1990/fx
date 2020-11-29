package com.paf.exercise.exercise.controller;

import com.paf.exercise.exercise.dto.ctrader.PositionRequest;
import com.paf.exercise.exercise.dto.ctrader.PositionResponse;
import com.paf.exercise.exercise.service.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private TelegramService telegramService;

    @GetMapping("/latest-positions")
    public List<PositionRequest> getLatestPositions() {
        List<PositionRequest> list = new ArrayList<>();
        telegramService.getLatestRequests(list);

        return list;
    }

    @PostMapping("/respond")
    public void respond(@RequestBody PositionResponse positionResponse) {
        telegramService.positionResponse(positionResponse);
    }
}
