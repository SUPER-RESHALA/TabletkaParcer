package com.simurg.Models;

public class Pharmacy {
    String pharmacyName;
    String cityName;
    String pharmacyAddress;
    Double price;
    Integer amount;
    Boolean isIndicated;

    public Pharmacy(String pharmacyName, String cityName, String pharmacyAddress, Double price, Integer amount, Boolean isIndicated) {
        this.pharmacyName = pharmacyName;
        this.cityName = cityName;
        this.pharmacyAddress = pharmacyAddress;
        this.price = price;
        this.amount = amount;
        this.isIndicated = isIndicated;
    }
public String getPharmaInfo(){
        String s= " Pname "+pharmacyName+"  city "+cityName+ "  Address "+pharmacyAddress+ " price "+price
                +"  amount "+ amount+ "indicated "+ isIndicated;
    // s.replaceAll("\\s","");
        return s;
}

}
