package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class GoldenWraps extends WristItem {

    public GoldenWraps() {
        title = "GoldenWraps";
        styleName = "wraps/golden_wraps";
        description = "GoldenWraps";
        menuStyleName = "potatoMenu";
        cost = 15000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.25f;
    }

}


