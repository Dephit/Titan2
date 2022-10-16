package com.mygdx.game.model.items.belts;

import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.items.wristWraps.WristItem;

public class BasicBelt extends BeltItem {

    public BasicBelt() {
        title = getLanguage().basicBelt;
        styleName = "belts/basic_belt";
        exerciseValue = 1.1f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().deadlift
        );
        cost = 7500;
    }


}

