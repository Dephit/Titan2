package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.ItemType;

public class WristItem extends ContiniousItem {


    public WristItem(){
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.bench);
        type = ItemType.WRAPS;
    }

}


