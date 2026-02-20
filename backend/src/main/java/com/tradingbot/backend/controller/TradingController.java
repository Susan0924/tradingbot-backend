package com.tradingbot.backend.controller;

import com.tradingbot.backend.service.TradingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.tradingbot.backend.model.Trade;

@RestController
@RequestMapping("/api/trading")
public class TradingController {

    private final TradingService tradingService;

    public TradingController(TradingService tradingService) {
        this.tradingService = tradingService;
    }

    @GetMapping ("/start")
    public String start() {
        tradingService.startBot();
        return "Bot Started";
    }

    @GetMapping("/stop")
    public String stop() {
        tradingService.stopBot();
        return "Bot Stopped";
    }

    @GetMapping("/status")
    public String status() {
        return tradingService.isRunning() ? "RUNNING" : "STOPPED";
    }
    @GetMapping("/balance")
    public double balance() {
        return tradingService.getBalance();
    }
    @GetMapping("/trades")
    public List<Trade> trades() {
        return tradingService.getTrades();
    }
}