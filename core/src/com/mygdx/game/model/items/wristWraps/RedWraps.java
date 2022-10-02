package com.mygdx.game.model.items.wristWraps;

public class RedWraps extends WristItem {

    public RedWraps() {
        title = "RedWraps";
        styleName = "wraps/red_wraps";
        description = "RedWraps";
        menuStyleName = "potatoMenu";
        cost = 5000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 40f;
    }

}

