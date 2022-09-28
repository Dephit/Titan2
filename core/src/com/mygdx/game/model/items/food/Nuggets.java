package com.mygdx.game.model.items.food;

import com.mygdx.game.Player;
import com.mygdx.game.model.items.Item;

public class Nuggets extends Item {

    public Nuggets(){
        title = "Nuggets";
        styleName = "nuggets";
        description = "Nuggets Description";
        menuStyleName = "potatoMenu";
        cost = 50;
    }

    @Override
    public void onUse(Player player) {
        player.exerciseManager.addHealth(25);
        ////player.refrigerator.removeItem(this);
    }
}
