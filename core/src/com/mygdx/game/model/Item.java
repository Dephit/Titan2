package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.BaseActor;
import com.mygdx.game.Player;

import java.util.Objects;

public class Item extends BaseActor {

    public int cost;

    public String title;

    public String styleName;

    public String description;

    public String menuStyleName;

    public void onUse(Player player){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(title, item.title) && Objects.equals(description, item.description);
    }

    public Actor getPopupWindow() {
        return null;
    }
}

