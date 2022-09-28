package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.ItemType;
import com.mygdx.game.model.KneesleavesItem;

public class BasicKneeSleaves extends KneesleavesItem {


    public BasicKneeSleaves(){
        title = "BasicKneeSleaves";
        styleName = "kneesleaves/basic_kneesleeves";
        description = "BasicKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 5000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 10f;
    }

    @Override
    public void onUse(Player player) {

    }
}

