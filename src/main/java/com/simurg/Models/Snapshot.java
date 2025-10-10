package com.simurg.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Snapshot {
   protected LocalDateTime date;
    protected List<Item> items;

    public Snapshot(LocalDateTime date, List<Item> items) {
        this.date = date;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
