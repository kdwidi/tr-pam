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
