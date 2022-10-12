package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class SquatThirdPerk extends PerkItem {

    public SquatThirdPerk() {
        title = "SquatThirdPerk";
        styleName = "perks/squat_first_perk";
        description = "SquatThirdPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.squat);
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(SquatSecondPerk.class.getName()) && super.isRequirementSatisfied(player);
    }

    @Override
    public float getExerciseMultiplier() {
        return 1.1f;
    }
}
