package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class SquatThirdPerk extends PerkItem {

    public SquatThirdPerk() {
        title = getLanguage().squatThirdPerk;
        exerciseValue = 1.1f;
        description = Language.getString(
                getLanguage().squatImprovePerk,
                getPercentages(exerciseValue)
        );
        styleName = "perks/squat_first_perk";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.squat);
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(SquatSecondPerk.class.getName()) && super.isRequirementSatisfied(player);
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return exerciseValue;
    }
}
