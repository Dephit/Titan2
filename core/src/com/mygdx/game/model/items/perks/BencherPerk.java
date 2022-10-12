package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class BencherPerk extends PerkItem {

    @Override
    public boolean canBeBought(Player player) {
        System.out.println(SquaterPerk.class.getName());
        return !player.inventoryManager.hasPerk(SquaterPerk.class.getName()) && !player.inventoryManager.hasPerk(DeadliftPerk.class.getName());
    }

    public BencherPerk() {
        title = "BencherPerk";
        styleName = "perks/bench_first_perk";
        description = "BencherPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.squat);
        conditionList.add(PlayerCondition.bench);
        conditionList.add(PlayerCondition.deadlift);
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return playerCondition == PlayerCondition.bench ? 1.2f : 0.8f;
    }
}

