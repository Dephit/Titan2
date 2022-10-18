package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class DeadliftFirstPerk extends PerkItem {

    public DeadliftFirstPerk() {
        title = getLanguage().deadliftFirstPerk;
        exerciseValue = 1.2f;
        description = getLanguage().deadliftImprovePerk;;
        styleName = "perks/deadlift_first_perk";
        cost = 5;
        childPerk.add(new BeltUsePerk());
        childPerk.add(new DeadliftSecondPerk());
        childPerk.add(new DeadliftThirdPerk());
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return exerciseValue;
    }
}

