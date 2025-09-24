package com.simurg.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Item {
    String itemName;
    String type;
    List<Pharmacy> pharma;

    public List<Pharmacy> getPharma() {
        return pharma;
    }
    public void setPharma(List<Pharmacy> pharma) {
        this.pharma = pharma;
    }

    public Item(String itemName, String type) {
        this.itemName = itemName;
        this.type = type;
    }

public  void printItem(){
    System.out.println("---------------------------------------------------");
    System.out.println("Name: "+this.itemName+" Type  "+ type);
    printPharma();
    System.out.println("---------------------------------------------------");
}
public void printPharma(){
        for (Pharmacy pharmacy:pharma){
            System.out.println(pharmacy.getPharmaInfo());
        }
}
}
