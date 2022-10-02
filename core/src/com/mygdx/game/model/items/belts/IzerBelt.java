package com.mygdx.game.model.items.belts;

public class IzerBelt extends BeltItem {

    public IzerBelt() {
        title = "IzerBelt";
        styleName = "belts/izer_belt";
        description = "IzerBelt";
        menuStyleName = "potatoMenu";
        cost = 12000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 40f;
    }

}

