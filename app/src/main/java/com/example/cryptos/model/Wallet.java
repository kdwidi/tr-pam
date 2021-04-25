package com.example.cryptos.model;

public class Wallet {
    private final String name;
    private final double cryptobalance;
    private final int idrbalance;

    public Wallet(String name, double cryptobalance, int idrbalance) {
        this.name = name;
        this.cryptobalance = cryptobalance;
        this.idrbalance = idrbalance;
    }

    public String getName() {
        return name;
    }

    public double getCryptobalance() {
        return cryptobalance;
    }

    public int getIdrbalance() {
        return idrbalance;
    }
}