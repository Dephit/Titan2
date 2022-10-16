package com.mygdx.game.model.items.wristWraps;

public class GoldenWraps extends WristItem {

    public GoldenWraps() {
        title = getLanguage().goldenWristWraps;
        styleName = "wraps/golden_wraps";
        exerciseValue = 1.25f;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().bench
        );
        menuStyleName = "potatoMenu";
        cost = 15000;
    }


}


