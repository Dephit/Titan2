package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class BenchFirstPerk extends PerkItem {

    public BenchFirstPerk() {
        title = getLanguage().bencherIPerk;
        exerciseValue = 1.2f;
        description = Language.getString(
                getLanguage().bencherImprovePerk,
                getPercentages(exerciseValue)
        );
        styleName = "perks/bench_first_perk";

        cost = 5;
        childPerk.add(new WristUsePerk());
        childPerk.add(new BenchSecondPerk());
        childPerk.add(new BenchThirdPerk());
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.bench);
    }


    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return exerciseValue;
    }
}

