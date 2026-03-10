package com.tradingbot.backend.service;

import com.tradingbot.backend.broker.MockBroker;
import com.tradingbot.backend.engine.ExecutionEngine;
import com.tradingbot.backend.model.Performance;
import com.tradingbot.backend.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.tradingbot.backend.model.Trade;


@Service
public class TradingService {
    @Autowired
    private MarketDataService marketDataService;
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

        engine = new ExecutionEngine(marketDataService, strategy, broker);

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

    public Performance getPerformance() {

        double balance = broker.getBalance();
        double initialBalance = 10000;

        List<Trade> trades = broker.getTrades();

        int totalTrades = trades.size();
        int winningTrades = 0;
        int losingTrades = 0;

        for (Trade trade : trades) {

            if (trade.getPnl() > 0)
                winningTrades++;
            else
                losingTrades++;

        }

        double profit = balance - initialBalance;

        double winRate = 0;

        if (totalTrades > 0) {
            winRate = ((double) winningTrades / totalTrades) * 100;
        }

        return new Performance(
                balance,
                profit,
                totalTrades,
                winningTrades,
                losingTrades,
                winRate
        );
    }
}