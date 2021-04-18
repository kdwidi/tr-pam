package com.example.cryptos.model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String name;
    private String telp;
    private final Wallet wallet;

    public User() {
        wallet = new Wallet();
    }

    public void testPrint() {
        System.out.println("Username: " + username);
        System.out.println("Name: " + name);
        System.out.println("Telp: " + telp);
    }

    public int setUser(DataSnapshot user) {
        if(user.getKey().contains("userid-")) {
            username = user.getKey().substring(7, user.getKey().length());
            name = user.child("name").getValue().toString();
            telp = user.child("telp").getValue().toString();
            wallet.setWallet(user.child("wallet"));
            return 1;
        }
        return -1;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getTelp() {
        return telp;
    }

    public Wallet getWallet() {
        return wallet;
    }

    class Wallet {
        private final Map<String, Double> currencies;

        public Wallet() {
            currencies = new HashMap<>();
        }

        public void testPrint() {
            for(String k : currencies.keySet()) {
                System.out.println(k + " - " + currencies.get(k));
            }
        }

        public void setWallet(DataSnapshot wallet) {
            currencies.clear();
            System.out.println();
            wallet.getChildren().forEach(currency -> {
                currencies.put(currency.getKey(), currency.getValue(Double.class));
            });
        }

        public Map<String, Double> get() {
            return currencies;
        }

        public Double getBalanceByCurrency(String currency) {
            for (String c : currencies.keySet()) {
                if (c.equals(currency)) return currencies.get(currency);
            }
            return null;
        }
    }
}
