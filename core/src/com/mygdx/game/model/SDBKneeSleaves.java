package com.mygdx.game.model;

import com.mygdx.game.Player;

public class SDBKneeSleaves extends ContiniousItem {

    SDBKneeSleaves() {
        title = "SDBKneeSleaves";
        styleName = "sdb_kneesleeves";
        description = "SDBKneeSleaves";
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
