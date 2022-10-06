package com.mygdx.game.model.items.perks;

public class SquatFirstPerk extends PerkItem {

    public SquatFirstPerk() {
        title = "SquatFirstPerk";
        styleName = "perks/squat_first_perk";
        description = "SquatFirstPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        childPerk.add(
                new KneeUsePerk()
        );
    }
}


