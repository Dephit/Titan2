package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;

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
        return 1.2f;
    }

    @Override
    public void onUse(Player player) {

    }
}

