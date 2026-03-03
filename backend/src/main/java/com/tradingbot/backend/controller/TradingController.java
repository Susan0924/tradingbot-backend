package com.tradingbot.backend.controller;

import com.tradingbot.backend.service.MarketDataService;
import com.tradingbot.backend.service.TradingService;
import com.tradingbot.backend.broker.MockBroker;
import com.tradingbot.backend.model.Candle;
import com.tradingbot.backend.model.Trade;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TradingController {

    private final MarketDataService marketDataService;
    private final TradingService tradingService;
    private final MockBroker broker;

    public TradingController(MarketDataService marketDataService,
                          TradingService tradingService,
                          MockBroker broker) {
        this.marketDataService = marketDataService;
        this.tradingService = tradingService;
        this.broker = broker;
    }

    // ✅ Basic health check
    @GetMapping("/ping")
    public String ping() {
        return "Backend is running 🚀";
    }

    // ✅ Get latest candles
    @GetMapping("/candles")
    public List<Candle> getCandles() {
        return marketDataService.getCandles();
    }

    // ✅ Get wallet balance
    @GetMapping("/balance")
    public double getBalance() {
        return broker.getWallet().getBalance();
    }

    // ✅ Get open position
    @GetMapping("/position")
    public Object getOpenPosition() {
        return broker.getOpenPosition();
    }

    // ✅ Get all trades
    @GetMapping("/trades")
    public List<Trade> getTrades() {
        return tradingService.getTrades();
    }

    // ✅ Get bot status
    @GetMapping("/status")
    public String getStatus() {
        return tradingService.isRunning() ? "RUNNING" : "STOPPED";
    }
}