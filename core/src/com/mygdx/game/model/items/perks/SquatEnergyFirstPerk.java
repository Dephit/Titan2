package com.mygdx.game.model.items.perks;

import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class SquatEnergyFirstPerk extends PerkItem {

    public SquatEnergyFirstPerk() {
        title = "SquatEnergyFirstPerk";
        styleName = "perks/squat_first_perk";
        description = "SquatEnergyFirstPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.squat);
    }

    @Override
    public float getEnergyMultiplier() {
        return 0.9f;
    }
}
