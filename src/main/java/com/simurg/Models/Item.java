package com.simurg.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Item {
    String itemName;
    String type;
    String producer;
    String link;
    List<Pharmacy> pharma;

    public List<Pharmacy> getPharma() {
        return pharma;
    }
    public void setPharma(List<Pharmacy> pharma) {
        this.pharma = pharma;
    }

    public Item(String itemName, String type, String producer, String link) {
        this.itemName = itemName;
        this.type = type;
        this.producer = producer;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getProducer() {
        return producer;
    }

    public String getType() {
        return type;
    }

    public String getItemName() {
        return itemName;
    }

    public  void printItem(){
    System.out.println("---------------------------------------------------");
    System.out.println("Name: "+this.itemName+" Type  "+ type+"   Link "+link +"  Producer "+producer);
    if (pharma!=null&&!pharma.isEmpty()){
        printPharma();
    }
    System.out.println("---------------------------------------------------");
}
public void printPharma(){
        for (Pharmacy pharmacy:pharma){
            System.out.println(pharmacy.getPharmaInfo());
        }
}
}
