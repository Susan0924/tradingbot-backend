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
    private List<Candle> candles = new ArrayList<>();

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
            candles.addAll(simulator.generateCandles(1));

            String signal = strategy.evaluate(candles);

            double lastPrice = candles.get(candles.size() - 1).getClose();

            if (signal.equals("BUY") || signal.equals("SELL")) {

                if (broker.getOpenPosition() == null) {
                    broker.openPosition(signal, lastPrice, 0.1);
                } else {
                    broker.closePosition(lastPrice);
                }
            }

            System.out.println("Signal: " + signal);

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