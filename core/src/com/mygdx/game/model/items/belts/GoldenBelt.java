package com.mygdx.game.model.items.belts;

import com.mygdx.game.PlayerCondition;

public class GoldenBelt extends BeltItem {

    public GoldenBelt() {
        title = "GoldenBelt";
        styleName = "belts/golden_belt";
        description = "GoldenBelt";
        menuStyleName = "potatoMenu";
        cost = 50000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.4f;
    }

}
