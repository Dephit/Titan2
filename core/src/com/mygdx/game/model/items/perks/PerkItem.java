package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.ItemType;
import com.mygdx.game.model.items.food.Nuggets;
import com.mygdx.game.model.items.food.Potato;

import java.util.ArrayList;

public class PerkItem extends ContiniousItem {


    public ArrayList<PerkItem> childPerk = new ArrayList<>();

    public boolean isRequirementSatisfied(Player player){
        return player.inventoryManager.perkPocket.hasEnough(cost);
    }

    public boolean canBeBought(Player player){
        return true;
    }

    public PerkItem(){
        //effectType = EffectType.PERMANENT;
        type = ItemType.PERK;
    }

    public void buy(Player player) {
        onUse(player);
        if(player.inventoryManager.perkPocket.buy(cost)){
            player.inventoryManager.perkContainer.addItem(this);
        }
    }
}





