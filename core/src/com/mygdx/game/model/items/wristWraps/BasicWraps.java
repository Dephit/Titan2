package com.mygdx.game.model.items.wristWraps;

public class BasicWraps extends WristItem {

    public BasicWraps() {
        title = "BasicWraps";
        styleName = "wraps/basic_wraps";
        description = "BasicWraps";
        menuStyleName = "potatoMenu";
        cost = 2500;
    }

    @Override
    public float getExerciseMultiplier() {
        return 1.2f;
    }

}

