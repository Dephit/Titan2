package com.mygdx.game.model.items.singlets;

import com.mygdx.game.Player;
import com.mygdx.game.model.items.kneesleaves.KneesleavesItem;

public class BasicSinglet extends SingletItem {

    public BasicSinglet() {
        title = "BasicSinglet";
        styleName = "singlets/basic_singlet";
        description = "BasicSinglet";
        menuStyleName = "potatoMenu";
        cost = 50000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 1f;
    }

    @Override
    public void onUse(Player player) {

    }
}

