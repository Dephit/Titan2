package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;

public class GoldenKneeSleaves extends KneesleavesItem {

    public GoldenKneeSleaves() {
        title = "GoldenKneeSleaves";
        styleName = "kneesleaves/golden_kneesleeves";
        description = "GoldenKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 15000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.4f;
    }

    @Override
    public void onUse(Player player) {

    }
}

