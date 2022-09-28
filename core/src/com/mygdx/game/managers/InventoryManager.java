package com.mygdx.game.managers;

import static sun.jvm.hotspot.debugger.win32.coff.DebugVC50X86RegisterEnums.TAG;

import com.mygdx.game.model.Container;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EquipmentContainer;
import com.mygdx.game.model.Inventory;
import com.mygdx.game.model.items.Item;
import com.mygdx.game.model.Pocket;
import com.mygdx.game.model.Refrigerator;

import org.graalvm.compiler.replacements.Log;

public class InventoryManager {

    public Container inventory = new Inventory();
    public EquipmentContainer equipmentContainer = new EquipmentContainer();
    public Refrigerator refrigerator = new Refrigerator();
    public Pocket pocket = new Pocket(100000);

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

    public boolean buyItemToEquipment(ContiniousItem item) {
        if(equipmentContainer.hasSpace()){
            if(pocket.buy(item.cost)){
                for (Item equipItem: equipmentContainer.getItems()) {
                    if(((ContiniousItem) equipItem).type == item.type){
                        System.out.println("TYPE_" + equipItem.title);
                        equipmentContainer.removeItem(equipItem);
                        break;
                    }
                }
                equipmentContainer.addItem(item);
                return true;
            }else return false;
        } else return false;
    }
}
