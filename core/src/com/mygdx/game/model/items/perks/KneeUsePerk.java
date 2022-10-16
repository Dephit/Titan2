package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.Player;

public class KneeUsePerk extends PerkItem {

    public KneeUsePerk() {
        title = getLanguage().kneesleavesUsePerk;
        description = Language.getString(
                getLanguage().squatEnergyImprovePerk,
                getPercentages(energyValue)
        );
        styleName = "perks/knee_use_perk";
        cost = 5;
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(SquatFirstPerk.class.getName()) && super.isRequirementSatisfied(player);
    }


}




