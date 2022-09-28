package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.KneesleavesItem;

public class SilverKneeSleaves extends KneesleavesItem {

    public SilverKneeSleaves() {
        title = "SilverKneeSleaves";
        styleName = "kneesleaves/silver_kneesleeves";
        description = "SilverKneeSleaves";
        menuStyleName = "potatoMenu";
        cost = 10000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 30f;
    }

    @Override
    public void onUse(Player player) {

    }
}

