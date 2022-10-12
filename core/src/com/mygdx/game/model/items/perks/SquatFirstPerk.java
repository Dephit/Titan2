package com.mygdx.game.model.items.perks;

import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class SquatFirstPerk extends PerkItem {

    public SquatFirstPerk() {
        title = "SquatFirstPerk";
        styleName = "perks/squat_first_perk";
        description = "SquatFirstPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        childPerk.add(new KneeUsePerk());
        childPerk.add(new SquatSecondPerk());
        childPerk.add(new SquatThirdPerk());
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.squat);

    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.2f;
    }


}



