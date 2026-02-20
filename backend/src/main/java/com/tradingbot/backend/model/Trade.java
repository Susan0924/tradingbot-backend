package com.tradingbot.backend.model;

import java.time.LocalDateTime;

public class Trade {

    private String type;
    private double entryPrice;
    private double exitPrice;
    private double pnl;
    private LocalDateTime time;

    public Trade(String type, double entryPrice, double exitPrice, double pnl) {
        this.type = type;
        this.entryPrice = entryPrice;
        this.exitPrice = exitPrice;
        this.pnl = pnl;
        this.time = LocalDateTime.now();
    }

    public String getType() { return type; }
    public double getEntryPrice() { return entryPrice; }
    public double getExitPrice() { return exitPrice; }
    public double getPnl() { return pnl; }
    public LocalDateTime getTime() { return time; }
}