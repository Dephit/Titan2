package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class BlackWraps extends WristItem {

    public BlackWraps() {
        title = getLanguage().blackWristWraps;
        styleName = "wraps/black_wraps";
        exerciseValue = 1.15f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().bench
        );

        cost = 5000;
    }

}


