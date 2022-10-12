package com.mygdx.game.model.items.belts;

import com.mygdx.game.PlayerCondition;

public class SDBBelt extends BeltItem {

    public SDBBelt() {
        title = "SDBBelt";
        styleName = "belts/sdb_belt";
        description = "SDBBelt";
        menuStyleName = "potatoMenu";
        cost = 20000;
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.3f;
    }

}

