package com.mygdx.game.model.items.kneesleaves;

import com.mygdx.game.Player;

public class BasicKneeSleaves extends KneesleavesItem {


    public BasicKneeSleaves(){
        title = getLanguage().basikKneesleaves;
        styleName = "kneesleaves/basic_kneesleeves";
        exerciseValue = 1.2f;
        menuStyleName = "potatoMenu";
        cost = 5000;
        description = getItemDescription(
                getPercentages(exerciseValue), getLanguage().squat
        );
    }


    @Override
    public void onUse(Player player) {

    }
}

