package com.mygdx.game.model.items;

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

    public String addItemDesc(String... values) {
        return String.format(getLanguage().itemAddDescription, values);
    }

    public String getItemDescription(String... values) {
        return String.format(getLanguage().itemBoostDescription, values);
    }

    public String getItemDescriptions(String... values) {
        return String.format(getLanguage().itemBoostDescriptions, values);
    }


    public void onUse(Player player) {

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

