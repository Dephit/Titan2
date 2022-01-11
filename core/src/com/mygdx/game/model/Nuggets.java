package com.mygdx.game.model;

import com.mygdx.game.Player;

public class Nuggets extends Item {

    Nuggets(){
        title = "Nuggets";
        styleName = "nuggets";
        description = "Nuggets Description";
        menuStyleName = "potatoMenu";
    }

    @Override
    public void onUse(Player player) {
        player.addHealth(25);
        player.refrigerator.removeItem(this);
    }
}
