package com.mygdx.game.model.shop;

import com.mygdx.game.model.items.kneesleaves.BasicKneeSleaves;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.items.kneesleaves.GoldenKneeSleaves;
import com.mygdx.game.model.items.kneesleaves.PlatinumKneeSleaves;
import com.mygdx.game.model.items.kneesleaves.SDBKneeSleaves;
import com.mygdx.game.model.items.kneesleaves.SilverKneeSleaves;

public class EquipmentShopMenu extends Container {

    public EquipmentShopMenu() {
        this.totalCapacity = 8;

        addItem(new PlatinumKneeSleaves());
        addItem(new GoldenKneeSleaves());
        addItem(new SilverKneeSleaves());
        addItem(new SDBKneeSleaves());
        addItem(new BasicKneeSleaves());
    }

}
