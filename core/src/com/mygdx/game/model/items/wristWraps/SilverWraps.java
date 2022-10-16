package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class SilverWraps extends WristItem {

    public SilverWraps() {
        title = getLanguage().silverWristWraps;
        styleName = "wraps/silver_wraps";
        menuStyleName = "potatoMenu";
        cost = 10000;
        exerciseValue = 1.2f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().bench
        );
    }


}

