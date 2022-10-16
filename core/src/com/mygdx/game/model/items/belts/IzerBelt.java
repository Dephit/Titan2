package com.mygdx.game.model.items.belts;

import com.mygdx.game.PlayerCondition;

public class IzerBelt extends BeltItem {

    public IzerBelt() {
        title = getLanguage().izerBelt;
        styleName = "belts/izer_belt";
        exerciseValue = 1.3f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().deadlift
        );
        cost = 12000;
    }


}

