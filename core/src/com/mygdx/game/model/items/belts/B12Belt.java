package com.mygdx.game.model.items.belts;

public class B12Belt extends BeltItem {

    public B12Belt() {
        title = "B12Belt";
        styleName = "belts/b12_belt";
        description = "B12Belt";
        menuStyleName = "potatoMenu";
        cost = 15000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 40f;
    }

}


