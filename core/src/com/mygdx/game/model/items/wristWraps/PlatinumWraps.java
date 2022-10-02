package com.mygdx.game.model.items.wristWraps;

public class PlatinumWraps extends WristItem {

    public PlatinumWraps() {
        title = "PlatinumWraps";
        styleName = "wraps/platinum_wraps";
        description = "PlatinumWraps";
        menuStyleName = "potatoMenu";
        cost = 20000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 40f;
    }

}
