package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class BenchThirdPerk extends PerkItem {

    public BenchThirdPerk() {
        title = "BenchThirdPerk";
        styleName = "perks/bench_first_perk";
        description = "BenchThirdPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.bench);
    }

    @Override
    public boolean isRequirementSatisfied(Player player) {
        return player.inventoryManager.hasPerk(BenchSecondPerk.class.getName()) && super.isRequirementSatisfied(player);
    }

    @Override
    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.2f;
    }
}
