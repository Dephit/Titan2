package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;

public class SDBKneeSleaves extends KneesleavesItem {

    public SDBKneeSleaves() {
        title = "SDBKneeSleaves";
        styleName = "kneesleaves/sdb_kneesleeves";
        description = "SDBKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 10000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 20f;
    }

    @Override
    public void onUse(Player player) {

    }
}


