package com.mygdx.game.model.items.food;

import com.mygdx.game.model.items.Item;

public class FoodItem extends Item {

    public String getAddValue(int i) {
        return i + "";
    }

    public int addValue = 25;
}
