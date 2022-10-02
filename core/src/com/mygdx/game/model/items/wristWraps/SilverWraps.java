package com.mygdx.game.model.items.wristWraps;

public class SilverWraps extends WristItem {

    public SilverWraps() {
        title = "SilverWraps";
        styleName = "wraps/silver_wraps";
        description = "SilverWraps";
        menuStyleName = "potatoMenu";
        cost = 10000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 40f;
    }

}

