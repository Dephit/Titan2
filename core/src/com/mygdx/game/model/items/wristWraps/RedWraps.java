package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class RedWraps extends WristItem {

    public RedWraps() {
        title = "RedWraps";
        styleName = "wraps/red_wraps";
        description = "RedWraps";
        menuStyleName = "potatoMenu";
        cost = 5000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.15f;
    }

}

