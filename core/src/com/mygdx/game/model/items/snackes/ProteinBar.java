package com.mygdx.game.model.items.snackes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.model.items.Item;
import com.mygdx.game.model.items.food.FoodItem;


public class ProteinBar extends FoodItem {


    public ProteinBar() {
        title = getLanguage().proteinBar;
        styleName = "protein_bar";
        description = addItemDesc(
                getAddValue(addValue),
                getLanguage().health
        );
        menuStyleName = "potatoMenu";
        cost = 50;
    }

    @Override
    public Actor getPopupWindow() {
        return super.getPopupWindow();
    }

    @Override
    public void onUse(Player player) {
        player.exerciseManager.addHealth(addValue);
    }
}
