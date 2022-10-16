package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;

public class SDBKneeSleaves extends KneesleavesItem {

    public SDBKneeSleaves() {
        title = getLanguage().sdbKneesleaves;
        styleName = "kneesleaves/sdb_kneesleeves";
        exerciseValue = 1.2f;
        menuStyleName = "potatoMenu";
        cost = 10000;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().squat
        );
    }


    @Override
    public void onUse(Player player) {

    }
}


