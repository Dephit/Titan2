package com.mygdx.game.model;

import com.mygdx.game.model.items.Item;
import com.mygdx.game.model.items.perks.PerkItem;

import java.util.Objects;

public class PerkContainer extends Container {

    public PerkContainer() {
        this.totalCapacity = 100;
        //addItem(new BasicKneeSleaves());
    }

    public boolean hasPerk(PerkItem item) {
        return hasPerk(item.getClass().getName());
    }

    public boolean hasPerk(String title) {
        boolean has = false;
        for (Item equipItem: getItems()) {
            if (equipItem.getClass().getName().equals(title)){
                has = true;
                break;
            }
        }
        return has;
    }
}
