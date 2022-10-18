package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class DeadliftPerk extends PerkItem {

    @Override
    public boolean canBeBought(Player player) {
        System.out.println(DeadliftPerk.class.getName());
        return !player.inventoryManager.hasPerk(SquaterPerk.class.getName()) && !player.inventoryManager.hasPerk(BencherPerk.class.getName());
    }

    public DeadliftPerk() {
        title = getLanguage().deadliftPerk;
        exerciseValue = 1.2f;
        description = getLanguage().deadliftImprovePerk;
        styleName = "perks/deadlift_first_perk";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.squat);
        conditionList.add(PlayerCondition.bench);
        conditionList.add(PlayerCondition.deadlift);
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return playerCondition == PlayerCondition.deadlift ? 1.2f : 0.8f;
    }
}
