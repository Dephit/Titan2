package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;

public class WristUsePerk extends PerkItem {

    public WristUsePerk() {
        title = getLanguage().wristUsePerk;
        description = getLanguage().wristUsePerkDesc;
        styleName = "perks/wrist_use_perk";
        cost = 5;
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(BenchFirstPerk.class.getName())  && super.isRequirementSatisfied(player);
    }
}
