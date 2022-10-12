package com.mygdx.game.model.items.singlets;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.items.kneesleaves.KneesleavesItem;

public class PlatinumSinglet extends SingletItem {

    public PlatinumSinglet() {
        title = "PlatinumSinglet";
        styleName = "singlets/platinum_singlet";
        description = "PlatinumSinglet";
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
