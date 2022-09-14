package com.mygdx.game.model;

public class Inventory extends Container {

    public Inventory() {
        this.totalCapacity = 4;
        addItem(new ProteinBar());
        addItem(new ProteinBar());
        addItem(new EnergyDrink());
        addItem(new EnergyDrink());

    }

}
