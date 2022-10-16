package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class DeadliftThirdPerk extends PerkItem {

    public DeadliftThirdPerk() {
        title = getLanguage().deadliftThirdPerk;
        exerciseValue = 1.2f;
        description = Language.getString(
                getLanguage().deadliftImprovePerk,
                getPercentages(exerciseValue)
        );
        title = "DeadliftThirdPerk";
        styleName = "perks/deadlift_first_perk";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.deadlift);
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(DeadliftSecondPerk.class.getName()) && super.isRequirementSatisfied(player);
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return exerciseValue;
    }
}
