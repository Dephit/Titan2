package com.mygdx.game.model.items.snackes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.model.items.Item;
import com.mygdx.game.model.items.food.FoodItem;

public class EnergyDrink extends FoodItem {

    public EnergyDrink() {
        title = getLanguage().energyDrink;
        styleName = "energy_drink";
        description = addItemDesc(
                getAddValue(addValue),
                getLanguage().health
        );
        menuStyleName = "potatoMenu";
        cost = 80;
    }

    @Override
    public Actor getPopupWindow() {
        return super.getPopupWindow();
    }

    @Override
    public void onUse(Player player) {
        player.exerciseManager.addEnergy(addValue);
        //player.refrigerator.removeItem(this);
    }
}
