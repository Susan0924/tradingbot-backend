package com.tradingbot.backend.engine;

import com.tradingbot.backend.model.Candle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarketDataSimulator {

    private double lastPrice = 1.0850;
    private final Random random = new Random();

    public List<Candle> generateCandles(int count) {

        List<Candle> candles = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            double open = lastPrice;

            double change = (random.nextDouble() - 0.5) * 0.002;
            double close = open + change;

            double high = Math.max(open, close) + random.nextDouble() * 0.001;
            double low = Math.min(open, close) - random.nextDouble() * 0.001;

            Candle candle = new Candle(
                    LocalDateTime.now().plusMinutes(i),
                    open,
                    high,
                    low,
                    close
            );

            candles.add(candle);
            lastPrice = close;
        }

        return candles;
    }
    public Candle getNextCandle() {

        double open = lastPrice;

        // Simulate small price movement
        double change = (Math.random() - 0.5) * 0.01;

        double close = open + change;
        double high = Math.max(open, close) + Math.random() * 0.005;
        double low = Math.min(open, close) - Math.random() * 0.005;

        lastPrice = close;

        return new Candle(
                LocalDateTime.now(),
                open,
                high,
                low,
                close
        );
    }
}
