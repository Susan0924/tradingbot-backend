package com.tradingbot.backend.strategy;

import com.tradingbot.backend.model.Candle;
import java.util.List;

public interface Strategy {

    String evaluate(List<Candle> candles);
}