package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class DeadliftFirstPerk extends PerkItem {

    public DeadliftFirstPerk() {
        title = "DeadliftFirstPerk";
        styleName = "perks/deadlift_first_perk";
        description = "DeadliftFirstPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        childPerk.add(new BeltUsePerk());
        childPerk.add(new DeadliftSecondPerk());
        childPerk.add(new DeadliftThirdPerk());
    }
}

