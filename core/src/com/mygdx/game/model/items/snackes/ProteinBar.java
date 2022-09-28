package com.mygdx.game.model.items.snackes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Player;
import com.mygdx.game.model.items.Item;


public class ProteinBar extends Item {

    public ProteinBar() {
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
