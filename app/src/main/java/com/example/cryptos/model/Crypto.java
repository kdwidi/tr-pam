package com.example.cryptos.model;

public  class Crypto {
    private final String name;
    private final double price;

    public Crypto(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
