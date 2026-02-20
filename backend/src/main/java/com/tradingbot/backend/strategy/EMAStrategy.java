package com.tradingbot.backend.strategy;

import com.tradingbot.backend.indicator.EMAIndicator;
import com.tradingbot.backend.model.Candle;

import java.util.List;

public class EMAStrategy implements Strategy {

    private EMAIndicator shortEma;
    private EMAIndicator longEma;

    public EMAStrategy(int shortPeriod, int longPeriod) {
        this.shortEma = new EMAIndicator(shortPeriod);
        this.longEma = new EMAIndicator(longPeriod);
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