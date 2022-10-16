package com.mygdx.game.model.items.belts;

import com.mygdx.game.PlayerCondition;

public class GoldenBelt extends BeltItem {

    public GoldenBelt() {
        title = getLanguage().goldenBelt;
        styleName = "belts/golden_belt";
        exerciseValue = 1.4f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().deadlift
        );
        cost = 50000;
    }


}
