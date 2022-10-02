package com.mygdx.game.model.items.singlets;

import com.mygdx.game.Player;
import com.mygdx.game.model.items.kneesleaves.KneesleavesItem;

public class RedSinglet extends SingletItem {

    public RedSinglet() {
        title = "RedSinglet";
        styleName = "singlets/red_singlet";
        description = "RedSinglet";
        menuStyleName = "potatoMenu";
        cost = 10000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 40f;
    }

    @Override
    public void onUse(Player player) {

    }
}

