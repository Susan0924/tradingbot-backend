package com.tradingbot.backend.broker;

public class MockBroker {

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

    public void closePosition(double currentPrice) {

        if (openPosition == null) {
            return;
        }

        double pnl = calculatePnL(currentPrice);
        wallet.updateBalance(pnl);

        System.out.println("Closed position at price: " + currentPrice);
        System.out.println("PnL: " + pnl);
        System.out.println("New Balance: " + wallet.getBalance());

        openPosition = null;
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

    public Position getOpenPosition() {
        return openPosition;
    }

    public Wallet getWallet() {
        return wallet;
    }
}