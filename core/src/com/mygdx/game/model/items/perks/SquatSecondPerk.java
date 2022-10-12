package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class SquatSecondPerk extends PerkItem {

    public SquatSecondPerk() {
        title = "SquatSecondPerk";
        styleName = "perks/squat_first_perk";
        description = "SquatSecondPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.squat);
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(KneeUsePerk.class.getName()) && super.isRequirementSatisfied(player);
    }

    @Override
    public float getExerciseMultiplier() {
        return 1.2f;
    }
}

