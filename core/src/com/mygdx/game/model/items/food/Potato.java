package com.mygdx.game.model.items.food;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.model.items.Item;

public class Potato extends FoodItem {


    public Potato(){
        title = getLanguage().potato;
        styleName = "potato";
        description = addItemDesc(
                getAddValue(addValue),
                getLanguage().health
        );
        menuStyleName = "potatoMenu";
        cost = 100;
        addValue = 50;
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


