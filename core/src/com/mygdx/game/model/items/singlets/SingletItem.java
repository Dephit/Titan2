package com.mygdx.game.model.items.singlets;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.ItemType;
import com.mygdx.game.model.items.kneesleaves.KneesleavesItem;

public class SingletItem extends ContiniousItem {

    public SingletItem(){
        effectType = EffectType.ON_EXERCISE;
        type = ItemType.SINGLET;
    }

}


