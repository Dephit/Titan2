package com.mygdx.game.model.items.snackes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Player;
import com.mygdx.game.model.items.Item;

public class EnergyDrink extends Item {

    public EnergyDrink() {
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
        player.exerciseManager.addEnergy(25);
        //player.refrigerator.removeItem(this);
    }
}
