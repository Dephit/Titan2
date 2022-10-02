package com.mygdx.game.model.items.belts;

public class GoldenBelt extends BeltItem {

    public GoldenBelt() {
        title = "GoldenBelt";
        styleName = "belts/golden_belt";
        description = "GoldenBelt";
        menuStyleName = "potatoMenu";
        cost = 50000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 80f;
    }

}
