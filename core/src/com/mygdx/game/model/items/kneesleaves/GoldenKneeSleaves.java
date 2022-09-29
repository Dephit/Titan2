package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;

public class GoldenKneeSleaves extends KneesleavesItem {

    public GoldenKneeSleaves() {
        title = "GoldenKneeSleaves";
        styleName = "kneesleaves/golden_kneesleeves";
        description = "GoldenKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 15000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 40f;
    }

    @Override
    public void onUse(Player player) {

    }
}

