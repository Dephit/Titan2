package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.ItemType;

public class KneesleavesItem extends ContiniousItem {


    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return exerciseValue;
    }

    public KneesleavesItem(){
        effectType = EffectType.ON_EXERCISE;
        type = ItemType.KNEESLEAVES;
        conditionList.add(PlayerCondition.squat);
    }

}
