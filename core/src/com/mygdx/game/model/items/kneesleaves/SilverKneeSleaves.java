package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;

public class SilverKneeSleaves extends KneesleavesItem {

    public SilverKneeSleaves() {
        title = "SilverKneeSleaves";
        styleName = "kneesleaves/silver_kneesleeves";
        description = "SilverKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 10000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.3f;
    }

    @Override
    public void onUse(Player player) {

    }
}

