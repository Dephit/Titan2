package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.Player;

public class BeltUsePerk extends PerkItem {

    public BeltUsePerk() {
        title = getLanguage().beltUsePerk;
        description = Language.getString(
                getLanguage().squatEnergyImprovePerk,
                getPercentages(energyValue)
        );
        styleName = "perks/belt_use_perk";
        cost = 5;
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(DeadliftFirstPerk.class.getName()) && super.isRequirementSatisfied(player);
    }
}
