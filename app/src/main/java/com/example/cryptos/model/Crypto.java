package com.example.cryptos.model;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public  class Crypto {
    private String name;
    private String price;

    public Crypto(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}







//public class Crypto {
//    private Map<String, Double> crypto;
//
//    public Crypto() {
//        crypto = new HashMap<>();
//    }
//
//    public void testPrint() {
//        for(String k : crypto.keySet()) {
//            System.out.println(k + " - " + crypto.get(k));
//        }
//    }
//
//    public int setCrypto(DataSnapshot cryptoDataSnapshot) {
//        crypto.clear();
//        if (cryptoDataSnapshot.getKey().equals("crypto")) {
//            cryptoDataSnapshot.getChildren().forEach(child -> {
//                crypto.put(child.getKey(), child.getValue(Double.class));
//            });
//        }
//        return 0;
//    }
//
//    public Map<String, Double> getMap() {
//        return crypto;
//    }
//
//    public Double getPriceByCurrency(String currency) {
//        for (String c : crypto.keySet()) {
//            if (c.equals(currency)) return crypto.get(currency);
//        }
//        return null;
//    }
//}
