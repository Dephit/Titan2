package com.mygdx.game.model.items.supplements;

import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class Creatine extends SupplementItem {

    public Creatine() {
        timeWillBeLast = 30L;
        title = "Creatine";
        styleName = "supplements/creatine";
        description = "Creatine";
        menuStyleName = "potatoMenu";
        cost = 500;

        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.bench);
        conditionList.add(PlayerCondition.squat);
        conditionList.add(PlayerCondition.deadlift);
    }

    @Override
    public float getExerciseMultiplier() {
        return 1.2f;
    }

    @Override
    public float getEnergyMultiplier() {
        return 0.9f;
    }

}
