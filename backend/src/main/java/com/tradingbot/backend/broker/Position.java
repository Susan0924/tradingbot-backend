package com.tradingbot.backend.broker;

public class Position {

    private String type; // BUY or SELL
    private double entryPrice;
    private double lotSize;

    public Position(String type, double entryPrice, double lotSize) {
        this.type = type;
        this.entryPrice = entryPrice;
        this.lotSize = lotSize;
    }

    public String getType() {
        return type;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public double getLotSize() {
        return lotSize;
    }
    public double calculatePnL(double currentPrice) {

        if (type.equals("BUY")) {
            return (currentPrice - entryPrice) * lotSize;
        }
        else if (type.equals("SELL")) {
            return (entryPrice - currentPrice) * lotSize;
        }

        return 0;
    }
}