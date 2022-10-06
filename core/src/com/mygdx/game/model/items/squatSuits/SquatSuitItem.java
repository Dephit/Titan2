package com.mygdx.game.model.items.squatSuits;

import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.ItemType;

public class SquatSuitItem extends ContiniousItem {

    public SquatSuitItem(){
        effectType = EffectType.WHILE_EQUIPPED;
        type = ItemType.SUIT;
        //conditionList.add(PlayerCondition.squat);
    }

}