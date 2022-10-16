package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;

public class GoldenKneeSleaves extends KneesleavesItem {

    public GoldenKneeSleaves() {
        title = getLanguage().goldenKneeSleaves;
        styleName = "kneesleaves/golden_kneesleeves";
        exerciseValue = 1.4f;
        menuStyleName = "potatoMenu";
        cost = 15000;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().squat
        );
    }


    @Override
    public void onUse(Player player) {

    }
}

