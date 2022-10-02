package com.mygdx.game.model.items.belts;

public class SDBBelt extends BeltItem {

    public SDBBelt() {
        title = "SDBBelt";
        styleName = "belts/sdb_belt";
        description = "SDBBelt";
        menuStyleName = "potatoMenu";
        cost = 20000;
    }

    @Override
    public float getExerciseMultiplier() {
        return 40f;
    }

}

