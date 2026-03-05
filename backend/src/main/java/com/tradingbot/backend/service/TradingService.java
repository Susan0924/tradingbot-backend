package com.tradingbot.backend.service;

import com.tradingbot.backend.broker.MockBroker;
import com.tradingbot.backend.engine.ExecutionEngine;
import com.tradingbot.backend.engine.MarketDataSimulator;
import com.tradingbot.backend.strategy.EMAStrategy;
import com.tradingbot.backend.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.tradingbot.backend.model.Trade;


@Service
public class TradingService {

    @Autowired
    private Strategy strategy;

    @Autowired
    private MockBroker broker;

    private ExecutionEngine engine;
    private Thread engineThread;


    public void startBot() {

        if (engine != null) {
            return;
        }

        MarketDataSimulator simulator = new MarketDataSimulator();

        engine = new ExecutionEngine(simulator, strategy, broker);

        engineThread = new Thread(engine::start);
        engineThread.start();

        System.out.println("Bot started...");
    }

    public void stopBot() {

        if (engine != null) {
            engine.stop();
            engine = null;
            engineThread = null;
        }
    }

    public boolean isRunning() {
        return engine != null;
    }
    public double getBalance() {
        if (broker == null) return 0;
        return broker.getWallet().getBalance();
    }
    public List<Trade> getTrades() {
        return broker.getTradeHistory();
    }
}