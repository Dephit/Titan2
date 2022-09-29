package com.mygdx.game.model;

import com.mygdx.game.PlayerCondition;

public class KneesleavesItem extends ContiniousItem {

    public KneesleavesItem(){
        effectType = EffectType.ON_EXERCISE;
        type = ItemType.KNEESLEAVES;
        conditionList.add(PlayerCondition.squat);
    }

}
