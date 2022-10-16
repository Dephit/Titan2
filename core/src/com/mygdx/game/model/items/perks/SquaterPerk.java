package com.mygdx.game.model.items.perks;


import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class SquaterPerk extends PerkItem {

    @Override
    public boolean canBeBought(Player player) {
        System.out.println(SquaterPerk.class.getName());
        return !player.inventoryManager.hasPerk(DeadliftPerk.class.getName()) && !player.inventoryManager.hasPerk(BencherPerk.class.getName());
    }

    public SquaterPerk() {
        title = getLanguage().squaterPerk;
        description = getLanguage().squaterPerkDescription;
        styleName = "perks/squat_first_perk";
        cost = 5;
        childPerk.add(new BencherPerk());
        childPerk.add(new DeadliftPerk());
        conditionList.add(PlayerCondition.squat);
        conditionList.add(PlayerCondition.bench);
        conditionList.add(PlayerCondition.deadlift);
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return playerCondition == PlayerCondition.squat ? 1.2f : 0.8f;
    }
}


