package com.mygdx.game.model.shop;

import com.mygdx.game.model.items.belts.B12Belt;
import com.mygdx.game.model.items.belts.BasicBelt;
import com.mygdx.game.model.items.belts.GoldenBelt;
import com.mygdx.game.model.items.belts.IzerBelt;
import com.mygdx.game.model.items.belts.SDBBelt;
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
import com.mygdx.game.model.items.supplements.Creatine;
import com.mygdx.game.model.items.wristWraps.BasicWraps;
import com.mygdx.game.model.items.wristWraps.BlackWraps;
import com.mygdx.game.model.items.wristWraps.GoldenWraps;
import com.mygdx.game.model.items.wristWraps.PlatinumWraps;
import com.mygdx.game.model.items.wristWraps.RedWraps;

public class EquipmentShopMenu extends Container {

    public EquipmentShopMenu() {
        this.totalCapacity = 100;

        addItem(new Creatine());

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

        addItem(new PlatinumWraps());
        addItem(new GoldenWraps());
        addItem(new BlackWraps());
        addItem(new RedWraps());
        addItem(new BasicWraps());

        addItem(new BasicBelt());
        addItem(new IzerBelt());
        addItem(new B12Belt());
        addItem(new SDBBelt());
        addItem(new GoldenBelt());
    }

}
