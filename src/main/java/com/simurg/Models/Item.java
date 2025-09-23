package com.simurg.Models;

import java.time.LocalDateTime;

public class Item {
    String pharmacyName;
    String cityName;
    String pharmacyAddress;
    Double price;
    String itemName;
    Integer amount;
    LocalDateTime date;
    String type;
    Boolean isIndicated;

    public Item(String pharmacyName, String cityName, String pharmacyAddress, Double price, String itemName, Integer amount, LocalDateTime date, String type, Boolean isIndicated) {
        this.pharmacyName = pharmacyName;
        this.cityName = cityName;
        this.pharmacyAddress = pharmacyAddress;
        this.price = price;
        this.itemName = itemName;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.isIndicated = isIndicated;
    }

    public Item(String itemName, String type) {
        this.itemName = itemName;
        this.type = type;
    }
}
