package com.mygdx.game.model.items.singlets;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.items.kneesleaves.KneesleavesItem;

public class GoldenSinglet extends KneesleavesItem {

    public GoldenSinglet() {
        title = "GoldenSinglet";
        styleName = "singlets/golden_singlet";
        description = "GoldenSinglet";
        menuStyleName = "potatoMenu";
        cost = 50000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.4f;
    }

    @Override
    public void onUse(Player player) {

    }
}

