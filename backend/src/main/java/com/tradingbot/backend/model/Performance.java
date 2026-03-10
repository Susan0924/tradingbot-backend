package com.tradingbot.backend.model;

public class Performance {

    private double balance;
    private double profit;
    private int totalTrades;
    private int winningTrades;
    private int losingTrades;
    private double winRate;

    public Performance(double balance, double profit,
                       int totalTrades, int winningTrades,
                       int losingTrades, double winRate) {

        this.balance = balance;
        this.profit = profit;
        this.totalTrades = totalTrades;
        this.winningTrades = winningTrades;
        this.losingTrades = losingTrades;
        this.winRate = winRate;
    }

    public double getBalance() { return balance; }
    public double getProfit() { return profit; }
    public int getTotalTrades() { return totalTrades; }
    public int getWinningTrades() { return winningTrades; }
    public int getLosingTrades() { return losingTrades; }
    public double getWinRate() { return winRate; }
}