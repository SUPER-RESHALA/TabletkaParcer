package com.simurg.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Shapshot {
    private final Long id;
   protected LocalDateTime date;
    protected List<Item> items;

    public Shapshot(final Long id, LocalDateTime date, List<Item> items) {
        this.id = id;
        this.date = date;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }
}
