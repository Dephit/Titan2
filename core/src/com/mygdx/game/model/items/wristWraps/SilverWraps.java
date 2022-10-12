package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class SilverWraps extends WristItem {

    public SilverWraps() {
        title = "SilverWraps";
        styleName = "wraps/silver_wraps";
        description = "SilverWraps";
        menuStyleName = "potatoMenu";
        cost = 10000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.2f;
    }

}

