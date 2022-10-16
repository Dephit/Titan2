package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class BasicWraps extends WristItem {

    public BasicWraps() {
        title = getLanguage().basicWristWraps;
        styleName = "wraps/basic_wraps";
        exerciseValue = 1.2f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().bench
        );

        cost = 2500;
    }


}

