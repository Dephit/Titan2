package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;

public class SDBKneeSleaves extends KneesleavesItem {

    public SDBKneeSleaves() {
        title = "SDBKneeSleaves";
        styleName = "kneesleaves/sdb_kneesleeves";
        description = "SDBKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 10000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.2f;
    }

    @Override
    public void onUse(Player player) {

    }
}


