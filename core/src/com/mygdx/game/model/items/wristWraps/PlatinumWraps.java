package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class PlatinumWraps extends WristItem {

    public PlatinumWraps() {
        title = "PlatinumWraps";
        styleName = "wraps/platinum_wraps";
        description = "PlatinumWraps";
        menuStyleName = "potatoMenu";
        cost = 20000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.5f;
    }

}
