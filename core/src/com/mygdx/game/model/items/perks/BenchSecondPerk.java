package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class BenchSecondPerk extends PerkItem {

    public BenchSecondPerk() {
        title = getLanguage().bencherIIPerk;
        exerciseValue = 1.2f;
        description = Language.getString(
                getLanguage().bencherImprovePerk,
                getPercentages(exerciseValue)
        );
        styleName = "perks/bench_first_perk";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.bench);
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(WristUsePerk.class.getName()) && super.isRequirementSatisfied(player);
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return exerciseValue;
    }
}

