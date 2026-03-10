package com.tradingbot.backend.strategy;

import com.tradingbot.backend.indicator.EMAIndicator;
import com.tradingbot.backend.model.Candle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EMAStrategy implements Strategy {

    private final EMAIndicator shortEma;
    private final EMAIndicator longEma;


    public EMAStrategy() {
        this.shortEma = new EMAIndicator(9);   // short period
        this.longEma = new EMAIndicator(21);  // long period
    }

    @Override
    public String evaluate(List<Candle> candles) {
        System.out.println("EMA Strategy evaluating...");

        List<Double> shortValues = shortEma.calculate(candles);
        List<Double> longValues = longEma.calculate(candles);

        if (shortValues.isEmpty() || longValues.isEmpty()) {
            return "HOLD";
        }

        double latestShort = shortValues.get(shortValues.size() - 1);
        double latestLong = longValues.get(longValues.size() - 1);

        System.out.printf(
                "EMA Strategy | EMA9: %.5f | EMA21: %.5f%n",
                latestShort, latestLong
        );

        if (latestShort > latestLong) {
            System.out.println("EMA Signal → BUY");
            return "BUY";
        }
        else if (latestShort < latestLong) {
            System.out.println("EMA Signal → SELL");
            return "SELL";
        }

        return "HOLD";
    }
}