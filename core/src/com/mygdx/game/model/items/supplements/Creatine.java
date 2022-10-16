package com.mygdx.game.model.items.supplements;

import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class Creatine extends SupplementItem {


    public Creatine() {
        timeWillBeLast = 30L;
        styleName = "supplements/creatine";
        title = getLanguage().creatineTitle;
        description = getItemDescription(getPercentages(exerciseValue), getLanguage().energy);

        menuStyleName = "potatoMenu";
        cost = 500;

        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.bench);
        conditionList.add(PlayerCondition.squat);
        conditionList.add(PlayerCondition.deadlift);
    }



    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return exerciseValue;
    }

    @Override
    public float getEnergyMultiplier() {
        return 0.9f;
    }

}
