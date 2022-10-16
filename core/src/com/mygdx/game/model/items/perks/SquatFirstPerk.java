package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class SquatFirstPerk extends PerkItem {

    public SquatFirstPerk() {
        title = getLanguage().squatFirstPerk;
        exerciseValue = 1.2f;
        description = Language.getString(
                getLanguage().squatImprovePerk,
                getPercentages(exerciseValue)
        );
        styleName = "perks/squat_first_perk";
        cost = 5;
        childPerk.add(new KneeUsePerk());
        childPerk.add(new SquatSecondPerk());
        childPerk.add(new SquatThirdPerk());
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.squat);

    }



}



