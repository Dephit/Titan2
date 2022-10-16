package com.mygdx.game.model.items.food;

import com.mygdx.game.Player;
import com.mygdx.game.model.items.Item;

public class Nuggets extends FoodItem {

    public Nuggets(){
        title = getLanguage().nuggets;
        styleName = "nuggets";
        description = addItemDesc(
                getAddValue(addValue),
                getLanguage().health
        );
        menuStyleName = "potatoMenu";
        cost = 50;
    }

    @Override
    public void onUse(Player player) {
        player.exerciseManager.addHealth(addValue);
        ////player.refrigerator.removeItem(this);
    }
}
