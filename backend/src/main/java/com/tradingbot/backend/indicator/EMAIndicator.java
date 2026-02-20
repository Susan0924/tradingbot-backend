package com.tradingbot.backend.indicator;

import com.tradingbot.backend.model.Candle;

import java.util.ArrayList;
import java.util.List;

public class EMAIndicator implements Indicator {

    private int period;

    public EMAIndicator(int period) {
        this.period = period;
    }

    @Override
    public List<Double> calculate(List<Candle> candles) {

        List<Double> emaValues = new ArrayList<>();

        if (candles.size() < period) {
            return emaValues;
        }

        double multiplier = 2.0 / (period + 1);

        double sum = 0.0;
        for (int i = 0; i < period; i++) {
            sum += candles.get(i).getClose();
        }

        double previousEma = sum / period;
        emaValues.add(previousEma);

        for (int i = period; i < candles.size(); i++) {
            double close = candles.get(i).getClose();
            double ema = ((close - previousEma) * multiplier) + previousEma;

            emaValues.add(ema);
            previousEma = ema;
        }

        return emaValues;
    }
}