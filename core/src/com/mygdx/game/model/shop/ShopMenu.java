package com.mygdx.game.model.shop;

import com.mygdx.game.model.Container;
import com.mygdx.game.model.items.food.Nuggets;
import com.mygdx.game.model.items.food.Potato;

public class ShopMenu extends Container {

    public ShopMenu() {
        this.totalCapacity = 8;

        addItem(new Potato());
        addItem(new Nuggets());
    }

}

