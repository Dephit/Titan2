package com.mygdx.game.model.items.wristWraps;

import com.mygdx.game.PlayerCondition;

public class PlatinumWraps extends WristItem {

    public PlatinumWraps() {
        title = getLanguage().platinumWristWraps;
        styleName = "wraps/platinum_wraps";
        exerciseValue = 1.5f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().bench
        );
        menuStyleName = "potatoMenu";
        cost = 20000;
    }


}
