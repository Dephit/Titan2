package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;

public class PlatinumKneeSleaves extends KneesleavesItem {

    public PlatinumKneeSleaves() {
        title = getLanguage().platinumKneesleaves;
        styleName = "kneesleaves/platinum_kneesleeves";
        exerciseValue = 1.5f;
        menuStyleName = "potatoMenu";
        cost = 20000;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().squat
        );
    }

    @Override
    public void onUse(Player player) {

    }

}
