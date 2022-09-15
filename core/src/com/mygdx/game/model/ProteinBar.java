package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Player;


public class ProteinBar extends Item {

    ProteinBar() {
        title = "Protein Bar";
        styleName = "protein_bar";
        description = "Protein Bar";
        menuStyleName = "potatoMenu";
        cost = 50;
    }

    @Override
    public Actor getPopupWindow() {
        return super.getPopupWindow();
    }

    @Override
    public void onUse(Player player) {
        player.exerciseManager.addHealth(25);
        //player.refrigerator.removeItem(this);
    }
}
