package com.tradingbot.backend.controller;

import com.tradingbot.backend.model.Candle;
import com.tradingbot.backend.service.MarketDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    private final MarketDataService marketDataService;

    public TestController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/candles")
    public List<Candle> getCandles() {
        return marketDataService.getCandles();
    }
}