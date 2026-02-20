package com.tradingbot.backend.broker;
import java.util.List;
import java.util.ArrayList;

import com.tradingbot.backend.model.Trade;


public class MockBroker {
    private List<Trade> tradeHistory = new ArrayList<>();
    private Wallet wallet;
    private Position openPosition;


    public MockBroker(double initialBalance) {
        this.wallet = new Wallet(initialBalance);
    }

    public void openPosition(String type, double price, double lotSize) {

        if (openPosition != null) {
            return;
        }

        openPosition = new Position(type, price, lotSize);
        System.out.println("Opened " + type + " at price: " + price);
    }
    private double calculatePnL(double currentPrice) {

        double entry = openPosition.getEntryPrice();
        double lot = openPosition.getLotSize();

        if (openPosition.getType().equals("BUY")) {
            return (currentPrice - entry) * lot * 100000;
        } else {
            return (entry - currentPrice) * lot * 100000;
        }
    }


    public void closePosition(double currentPrice) {

        if (openPosition == null) {
            System.out.println("No open position to close");
            return;
        }

        System.out.println("Closing position...");

        double pnl = openPosition.calculatePnL(currentPrice);

        System.out.println("Closed position at price: " + currentPrice);
        System.out.println("PnL: " + pnl);

        tradeHistory.add(new Trade(
                openPosition.getType(),
                openPosition.getEntryPrice(),
                currentPrice,
                pnl
        ));

        wallet.updateBalance(pnl);

        openPosition = null;
    }


    public Position getOpenPosition() {
        return openPosition;
    }

    public Wallet getWallet() {
        return wallet;
    }
    public List<Trade> getTradeHistory() {
        return tradeHistory;
    }
    public boolean hasOpenPosition() {
        return openPosition != null;
    }
}