package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;

public class KneeUsePerk extends PerkItem {

    public KneeUsePerk() {
        title = "KneeUsePerk";
        styleName = "perks/knee_use_perk";
        description = "KneeUsePerk";
        menuStyleName = "potatoMenu";
        cost = 5;
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(SquatFirstPerk.class.getName()) && super.isRequirementSatisfied(player);
    }


}




