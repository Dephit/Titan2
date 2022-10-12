package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;

public class PlatinumKneeSleaves extends KneesleavesItem {

    public PlatinumKneeSleaves() {
        title = "PlatinumKneeSleaves";
        styleName = "kneesleaves/platinum_kneesleeves";
        description = "PlatinumKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 20000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.5f;
    }

    @Override
    public void onUse(Player player) {

    }
}
