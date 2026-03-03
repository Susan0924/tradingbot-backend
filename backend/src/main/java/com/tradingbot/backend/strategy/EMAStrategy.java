package com.tradingbot.backend.strategy;

import com.tradingbot.backend.indicator.EMAIndicator;
import com.tradingbot.backend.model.Candle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EMAStrategy implements Strategy {

    private final EMAIndicator shortEma;
    private final EMAIndicator longEma;

    // ✅ No-arg constructor so Spring can create bean
    public EMAStrategy() {
        this.shortEma = new EMAIndicator(9);   // short period
        this.longEma = new EMAIndicator(21);  // long period
    }

    @Override
    public String evaluate(List<Candle> candles) {

        List<Double> shortValues = shortEma.calculate(candles);
        List<Double> longValues = longEma.calculate(candles);

        if (shortValues.isEmpty() || longValues.isEmpty()) {
            return "HOLD";
        }

        double latestShort = shortValues.get(shortValues.size() - 1);
        double latestLong = longValues.get(longValues.size() - 1);

        if (latestShort > latestLong) {
            return "BUY";
        } else if (latestShort < latestLong) {
            return "SELL";
        }

        return "HOLD";
    }
}