package com.mygdx.game.model.shop;

import com.mygdx.game.model.items.kneesleaves.BasicKneeSleaves;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.items.kneesleaves.GoldenKneeSleaves;
import com.mygdx.game.model.items.kneesleaves.PlatinumKneeSleaves;
import com.mygdx.game.model.items.kneesleaves.SDBKneeSleaves;
import com.mygdx.game.model.items.kneesleaves.SilverKneeSleaves;
import com.mygdx.game.model.items.singlets.BasicSinglet;
import com.mygdx.game.model.items.singlets.BlackSinglet;
import com.mygdx.game.model.items.singlets.GoldenSinglet;
import com.mygdx.game.model.items.singlets.PlatinumSinglet;
import com.mygdx.game.model.items.singlets.RedSinglet;

public class EquipmentShopMenu extends Container {

    public EquipmentShopMenu() {
        this.totalCapacity = 100;

        addItem(new PlatinumKneeSleaves());
        addItem(new GoldenKneeSleaves());
        addItem(new SilverKneeSleaves());
        addItem(new SDBKneeSleaves());
        addItem(new BasicKneeSleaves());
        addItem(new PlatinumSinglet());
        addItem(new GoldenSinglet());
        addItem(new BlackSinglet());
        addItem(new RedSinglet());
        addItem(new BasicSinglet());
    }

}
