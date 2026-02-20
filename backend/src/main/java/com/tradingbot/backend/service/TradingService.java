package com.tradingbot.backend.service;

import com.tradingbot.backend.broker.MockBroker;
import com.tradingbot.backend.engine.ExecutionEngine;
import com.tradingbot.backend.engine.MarketDataSimulator;
import com.tradingbot.backend.strategy.EMAStrategy;
import org.springframework.stereotype.Service;
import java.util.List;
import com.tradingbot.backend.model.Trade;


@Service
public class TradingService {

    private ExecutionEngine engine;
    private Thread engineThread;
    private  MockBroker broker;


    public void startBot() {

        if (engine != null) {
            return;
        }

        MarketDataSimulator simulator = new MarketDataSimulator();
        EMAStrategy strategy = new EMAStrategy(5, 10);
        broker = new MockBroker(100000);
        engine = new ExecutionEngine(simulator, strategy, broker);

        engineThread = new Thread(engine::start);
        engineThread.start();
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