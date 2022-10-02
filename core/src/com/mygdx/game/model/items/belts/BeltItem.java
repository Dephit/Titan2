package com.mygdx.game.model.items.belts;

import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.ItemType;

public class BeltItem extends ContiniousItem {


    public BeltItem(){
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.deadlift);
        type = ItemType.BELT;
    }

}


