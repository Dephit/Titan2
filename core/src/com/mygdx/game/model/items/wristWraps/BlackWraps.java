package com.mygdx.game.model.items.wristWraps;

public class BlackWraps extends WristItem {

    public BlackWraps() {
        title = "BlackWraps";
        styleName = "wraps/black_wraps";
        description = "BlackWraps";
        menuStyleName = "potatoMenu";
        cost = 5000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 40f;
    }

}


