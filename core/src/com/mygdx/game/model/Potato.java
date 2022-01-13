package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Player;

public class Potato extends Item {

    Potato(){
        title = "Potato";
        styleName = "potato";
        description = "Potato Description";
        menuStyleName = "potatoMenu";
        cost = 100;
    }

    @Override
    public Actor getPopupWindow() {
        return super.getPopupWindow();
    }

    @Override
    public void onUse(Player player) {
        player.addHealth(50);
        player.refrigerator.removeItem(this);
    }
}

