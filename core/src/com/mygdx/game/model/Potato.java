package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Player;

public class Potato extends Item {

    Potato(){
        title = "Potato";
        styleName = "potato";
        description = "The potato is a\n" +
                " starchy tuber of \n" +
                "the plant Solanum \n" +
                "tuberosum and is \n" +
                "a root vegetable\n native to \n" +
                "the Americas. \n" +
                "The plant is \n" +
                "a perennial in the \n" +
                "nightshade family\n " +
                "Solanaceae";
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

