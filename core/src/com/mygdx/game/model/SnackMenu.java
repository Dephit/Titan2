package com.mygdx.game.model;

public class SnackMenu extends Container {

    public SnackMenu() {
        this.totalCapacity = 8;

        addItem(new EnergyDrink());
        addItem(new ProteinBar());
    }

}
