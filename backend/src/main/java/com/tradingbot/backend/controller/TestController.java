package com.tradingbot.backend.controller;

import com.tradingbot.backend.engine.MarketDataSimulator;
import com.tradingbot.backend.model.Candle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public List<Candle> testMarketData() {

        MarketDataSimulator simulator = new MarketDataSimulator();
        return simulator.generateCandles(5);
    }
}
