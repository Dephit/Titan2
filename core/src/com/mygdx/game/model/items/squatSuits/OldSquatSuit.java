package com.mygdx.game.model.items.squatSuits;

import com.mygdx.game.Player;

public class OldSquatSuit extends SquatSuitItem {

    public OldSquatSuit(){
        title = "OldSquatSuit";
        styleName = "squatsuits/old_squat_suit";
        description = "OldSquatSuit";
        menuStyleName = "potatoMenu";
        cost = 5000;
    }

    @Override
    public void onUse(Player player) {
        player.exerciseManager.squatExr.setLVL(player.exerciseManager.squatExr.LVL + 2, false);
    }

    @Override
    public void onRemove(Player player) {
        player.exerciseManager.squatExr.setLVL(player.exerciseManager.squatExr.LVL - 2, false);
    }
}
