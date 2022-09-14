package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Player;

public class EnergyDrink extends Item {

    EnergyDrink() {
        title = "Energy Drink";
        styleName = "energy_drink";
        description = "Energy Drink";
        menuStyleName = "potatoMenu";
        cost = 80;
    }

    @Override
    public Actor getPopupWindow() {
        return super.getPopupWindow();
    }

    @Override
    public void onUse(Player player) {
        player.addEnergy(25);
        //player.refrigerator.removeItem(this);
    }
}
