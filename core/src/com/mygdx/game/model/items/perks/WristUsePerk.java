package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;

public class WristUsePerk extends PerkItem {

    public WristUsePerk() {
        title = "WristUsePerk";
        styleName = "perks/wrist_use_perk";
        description = "WristUsePerk";
        menuStyleName = "potatoMenu";
        cost = 5;
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(new WristUsePerk());
    }
}
