package com.mygdx.game.model.items.perks;

public class DeadliftFirstPerk extends PerkItem {

    public DeadliftFirstPerk() {
        title = "DeadliftFirstPerk";
        styleName = "perks/deadlift_first_perk";
        description = "DeadliftFirstPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        childPerk.add(
                new BeltUsePerk()
        );
    }
}
