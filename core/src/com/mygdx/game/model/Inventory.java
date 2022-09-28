package com.mygdx.game.model;

import com.mygdx.game.model.items.snackes.EnergyDrink;
import com.mygdx.game.model.items.snackes.ProteinBar;

public class Inventory extends Container {

    public Inventory() {
        this.totalCapacity = 4;
        addItem(new ProteinBar());
        addItem(new ProteinBar());
        addItem(new EnergyDrink());
        addItem(new EnergyDrink());

    }

}

