package com.mygdx.game.model.items.singlets;

import com.mygdx.game.Player;
import com.mygdx.game.model.items.kneesleaves.KneesleavesItem;


public class BlackSinglet extends SingletItem {

    public BlackSinglet() {
        title = "BlackSinglet";
        styleName = "singlets/black_singlet";
        description = "BlackSinglet";
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
