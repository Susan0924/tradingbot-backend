package com.tradingbot.backend.service;

import com.tradingbot.backend.model.Candle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketDataService {

    private final List<Candle> candles = new ArrayList<>();

    public List<Candle> getCandles() {
        return candles;
    }

    public void addCandle(Candle candle) {
        candles.add(candle);
        if (candles.size() > 200) {
            candles.remove(0); // keep only last 200 candles
        }
    }

    @Scheduled(fixedRate = 60000) // every 60 seconds
    public void fetchMarketData() {
        System.out.println("Polling EURUSD data...");
        // 1. Call Forex API
        // 2. Parse response
        // 3. Create Candle
        // 4. addCandle(candle)
        // 5. Run strategy
    }
}
