package com.tradingbot.backend.engine;

import com.tradingbot.backend.broker.MockBroker;
import com.tradingbot.backend.model.Candle;
import com.tradingbot.backend.service.MarketDataService;
import com.tradingbot.backend.strategy.Strategy;

import java.util.List;

public class ExecutionEngine {

    private MarketDataService marketDataService;
    private Strategy strategy;
    private MockBroker broker;

    private boolean running = false;

    public ExecutionEngine(MarketDataService marketDataService,
                           Strategy strategy,
                           MockBroker broker) {
        this.marketDataService = marketDataService;
        this.strategy = strategy;
        this.broker = broker;
    }

    public void start() {

        running = true;
        System.out.println("Bot started...");

        // wait until first candle arrives
        while (marketDataService.getCandles().isEmpty()) {
            System.out.println("Waiting for first candle...");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
        }

        while (running) {

            List<Candle> candles = marketDataService.getCandles();

            System.out.println("Candles available: " + candles.size());

            if (candles.size() < 21) {
                try { Thread.sleep(2000); } catch (Exception e) {}
                continue;
            }

            Candle lastCandle = candles.get(candles.size() - 1);

            String signal = strategy.evaluate(candles);

            if(signal.equals("HOLD")) {
                try { Thread.sleep(2000); } catch (Exception e) {}
                continue;
            }

            double price = lastCandle.getClose();

            if (broker.hasOpenPosition()) {

                if (!broker.getOpenPosition().getType().equals(signal)) {
                    broker.closePosition(price);
                    broker.openPosition(signal, price, 1);
                }

            } else {
                broker.openPosition(signal, price, 1);
            }

            System.out.printf("Signal: %s | Price: %.5f%n", signal, price);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stop() {
        running = false;
    }
}