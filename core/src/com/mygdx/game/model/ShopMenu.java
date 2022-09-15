package com.mygdx.game.model;

public class ShopMenu extends Container {

    public ShopMenu() {
        this.totalCapacity = 8;

        addItem(new Potato());
        addItem(new Nuggets());
    }

}

