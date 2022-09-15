package com.mygdx.game.model;

import com.mygdx.game.Player;

public class Nuggets extends Item {

    Nuggets(){
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
