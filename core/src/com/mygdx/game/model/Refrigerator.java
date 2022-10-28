package com.mygdx.game.model;

import com.mygdx.game.model.items.food.Nuggets;
import com.mygdx.game.model.items.food.Potato;

public class Refrigerator extends Container {

    public Refrigerator(){
        this.totalCapacity = 8;

        addItem(new Potato());
        addItem(new Potato());
        addItem(new Nuggets());
        addItem(new Potato());
        addItem(new Nuggets());
    }


}

