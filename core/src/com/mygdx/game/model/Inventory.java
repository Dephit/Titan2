package com.mygdx.game.model;

public class Inventory extends Container {

    public Inventory() {
        this.totalCapacity = 4;
        addItem(new Potato());
        addItem(new Potato());
        addItem(new Potato());
        addItem(new Potato());

    }

}
