package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;

public class BeltUsePerk extends PerkItem {

    public BeltUsePerk() {
        title = "BeltUsePerk";
        styleName = "perks/belt_use_perk";
        description = "BeltUsePerk";
        menuStyleName = "potatoMenu";
        cost = 5;
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(new BeltUsePerk());
    }
}
