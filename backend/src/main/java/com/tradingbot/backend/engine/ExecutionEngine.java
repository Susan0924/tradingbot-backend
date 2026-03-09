package com.tradingbot.backend.engine;

import com.tradingbot.backend.broker.MockBroker;
import com.tradingbot.backend.model.Candle;
import com.tradingbot.backend.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

public class ExecutionEngine {

    private MarketDataSimulator simulator;
    private Strategy strategy;
    private MockBroker broker;

    private boolean running = false;
    private final List<Candle> candles = new ArrayList<>();

    public ExecutionEngine(MarketDataSimulator simulator,
                           Strategy strategy,
                           MockBroker broker) {
        this.simulator = simulator;
        this.strategy = strategy;
        this.broker = broker;

    }

    public void start() {

        running = true;
        System.out.println("Bot started...");

        // preload initial candles
        candles.addAll(simulator.generateCandles(20));

        while (running) {

            // generate one new candle
            Candle candle = simulator.getNextCandle();
            candles.add(candle);

            if (candles.size() > 200) {
                candles.remove(0);
            }

            String signal = strategy.evaluate(candles);
            double price = candle.getClose();

            if (!"BUY".equals(signal) && !"SELL".equals(signal)) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                continue;
            }

            if (broker.hasOpenPosition()) {

                if (!broker.getOpenPosition().getType().equals(signal)) {
                    broker.closePosition(price);
                    broker.openPosition(signal, price, 1);
                }

            } else {
                broker.openPosition(signal, price, 1);
            }
            System.out.println("Signal: " + signal);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
