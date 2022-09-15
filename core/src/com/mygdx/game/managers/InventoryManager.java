package com.mygdx.game.managers;

import com.mygdx.game.model.Container;
import com.mygdx.game.model.Inventory;
import com.mygdx.game.model.Item;
import com.mygdx.game.model.Pocket;
import com.mygdx.game.model.Refrigerator;

public class InventoryManager {

    public Container inventory = new Inventory();
    public Refrigerator refrigerator = new Refrigerator();
    public Pocket pocket = new Pocket(1000);

    public boolean buyItemToRefrigerator(Item item) {
        if(refrigerator.hasSpace()){
            if(pocket.buy(item.cost)){
                refrigerator.addItem(item);
                return true;
            }else return false;
        }else return false;
    }

    public boolean buyItemToInventory(Item item) {
        if(inventory.hasSpace()){
            if(pocket.buy(item.cost)){
                inventory.addItem(item);
                return true;
            }else return false;
        } else return false;
    }
}
