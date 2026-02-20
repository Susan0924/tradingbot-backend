package com.tradingbot.backend.broker;

public class Wallet {

    private double balance;

    public Wallet(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double pnl) {
        this.balance += pnl;
    }
}