package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class BlackWraps extends WristItem {

    public BlackWraps() {
        title = "BlackWraps";
        styleName = "wraps/black_wraps";
        description = "BlackWraps";
        menuStyleName = "potatoMenu";
        cost = 5000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.15f;
    }

}


