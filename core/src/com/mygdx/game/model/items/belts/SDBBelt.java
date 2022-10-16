package com.mygdx.game.model.items.belts;

import com.mygdx.game.PlayerCondition;

public class SDBBelt extends BeltItem {

    public SDBBelt() {
        title = getLanguage().sbdBelt;
        styleName = "belts/sdb_belt";
        exerciseValue = 1.3f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().deadlift
        );
        cost = 20000;
    }


}

