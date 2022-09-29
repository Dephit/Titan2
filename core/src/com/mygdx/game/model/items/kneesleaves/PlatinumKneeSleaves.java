package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;

public class PlatinumKneeSleaves extends KneesleavesItem {

    public PlatinumKneeSleaves() {
        title = "PlatinumKneeSleaves";
        styleName = "kneesleaves/platinum_kneesleeves";
        description = "PlatinumKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 20000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 50f;
    }

    @Override
    public void onUse(Player player) {

    }
}
