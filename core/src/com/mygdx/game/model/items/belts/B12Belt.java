package com.mygdx.game.model.items.belts;

import com.mygdx.game.PlayerCondition;

public class B12Belt extends BeltItem {

    public B12Belt() {
        title = getLanguage().b12BeltTitle;
        styleName = "belts/b12_belt";
        exerciseValue = 1.3f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().deadlift
        );
        cost = 15000;
    }


}


