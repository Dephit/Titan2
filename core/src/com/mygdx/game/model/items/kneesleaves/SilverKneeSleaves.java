package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;

public class SilverKneeSleaves extends KneesleavesItem {

    public SilverKneeSleaves() {
        title = getLanguage().silerKneeSleaves;
        styleName = "kneesleaves/silver_kneesleeves";
        menuStyleName = "potatoMenu";
        exerciseValue = 1.3f;
        cost = 10000;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().squat
        );
    }



    @Override
    public void onUse(Player player) {

    }
}

