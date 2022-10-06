package com.mygdx.game.model.items.belts;

import com.mygdx.game.model.items.wristWraps.WristItem;

public class BasicBelt extends BeltItem {

    public BasicBelt() {
        title = "BasicBelt";
        styleName = "belts/basic_belt";
        description = "BasicBelt";
        menuStyleName = "potatoMenu";
        cost = 7500;
    }

    @Override
    public float getExerciseMultiplier() {
        return 1.2f;
    }

}

