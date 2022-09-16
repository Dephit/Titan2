package com.mygdx.game.model;

import com.mygdx.game.Player;

public class BasicKneeSleaves extends ContiniousItem {

    BasicKneeSleaves(){
        title = "BasicKneeSleaves";
        styleName = "basic_kneesleeves";
        description = "BasicKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 50;
    }

    @Override
    public float getExerciseMultiplier() {
        return 10f;
    }

    @Override
    public void onUse(Player player) {

    }
}

