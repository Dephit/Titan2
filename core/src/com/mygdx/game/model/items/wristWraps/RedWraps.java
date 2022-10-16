package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class RedWraps extends WristItem {

    public RedWraps() {
        title = getLanguage().redWristWraps;
        styleName = "wraps/red_wraps";
        exerciseValue = 1.15f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().bench
        );
        menuStyleName = "potatoMenu";
        cost = 5000;
    }


}

