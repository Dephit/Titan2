package com.mygdx.game.managers;

import com.mygdx.game.model.Container;
import com.mygdx.game.model.Inventory;
import com.mygdx.game.model.Item;
import com.mygdx.game.model.Pocket;
import com.mygdx.game.model.Refrigerator;

public class InventoryManager {

    public Container inventory = new Inventory();
    public Refrigerator refrigerator = new Refrigerator();
    public Pocket pocket = new Pocket(75);

    public boolean buyItemToRefrigerator(Item item) {
        if(pocket.buy(item.cost)){
            refrigerator.addItem(item);
            return true;
        }else return false;
    }
}
