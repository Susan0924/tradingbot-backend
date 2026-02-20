package com.tradingbot.backend.indicator;

import com.tradingbot.backend.model.Candle;
import java.util.List;

public interface Indicator {

    List<Double> calculate(List<Candle> candles);
}