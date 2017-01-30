package com.example.sergey.organizer.entity;

public class ChooseMenu {

    private int id;
    private int item;

    public ChooseMenu(int id, int item) {
        this.id = id;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public int getItemID() {
        return item;
    }
}
