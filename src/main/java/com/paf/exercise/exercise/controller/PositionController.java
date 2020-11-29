package com.paf.exercise.exercise.controller;

import com.paf.exercise.exercise.dto.ctrader.ClosePositionRequest;
import com.paf.exercise.exercise.dto.ctrader.OpenPositionRequest;
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

    @GetMapping("/latest-open-positions")
    public List<OpenPositionRequest> getLatestOpenPositions() {
        List<OpenPositionRequest> list = new ArrayList<>();
        telegramService.getLatestOpenPositions(list);

        return list;
    }

    @GetMapping("/latest-close-positions")
    public List<ClosePositionRequest> getLatestClosePositions() {
        List<ClosePositionRequest> list = new ArrayList<>();
        telegramService.getLatestClosePositions(list);

        return list;
    }

    @PostMapping("/respond")
    public void respond(@RequestBody PositionResponse positionResponse) {
        telegramService.positionResponse(positionResponse);
    }
}
