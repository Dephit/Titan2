package com.mygdx.game.model.shop;

import com.mygdx.game.model.Container;
import com.mygdx.game.model.items.snackes.EnergyDrink;
import com.mygdx.game.model.items.snackes.ProteinBar;

public class SnackMenu extends Container {

    public SnackMenu() {
        this.totalCapacity = 8;

        addItem(new EnergyDrink());
        addItem(new ProteinBar());
    }

}
