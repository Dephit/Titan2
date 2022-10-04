package com.mygdx.game.model;

import com.mygdx.game.Player;
import com.mygdx.game.model.items.Item;
import com.mygdx.game.model.items.supplements.SupplementItem;

import java.util.ArrayList;

public class Day {
    public int currentDay = 0;
    public int currentTime = 0;

    public void addTime(Player player) {
        ArrayList<SupplementItem> items = new ArrayList<>();
        for (Item sup: player.inventoryManager.supplements.getItems()) {
            if(((ContiniousItem) sup).type == ItemType.SUPPLEMENT){
                ((SupplementItem) sup).timeInUseLeft ++;
                if(((SupplementItem) sup).timeInUseLeft >= ((SupplementItem) sup).timeWillBeLast){
                    items.add(((SupplementItem) sup));
                }
            }
        }
        for (SupplementItem i:items) {
            player.inventoryManager.supplements.removeItem(i);
        }
        currentTime++;
        if (currentTime > 30) {
            currentDay++;
            currentTime = 0;
        }
    }
}
