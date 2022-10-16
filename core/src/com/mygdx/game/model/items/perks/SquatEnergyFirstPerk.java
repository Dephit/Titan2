package com.mygdx.game.model.items.perks;

import com.mygdx.game.Language;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.EffectType;

public class SquatEnergyFirstPerk extends PerkItem {

    public SquatEnergyFirstPerk() {
        title = getLanguage().squatEnergyFirstPerk;
        energyValue = 0.9f;
        description = Language.getString(
                getLanguage().squatEnergyImprovePerk,
                getPercentages(energyValue)
        );

        styleName = "perks/squat_first_perk";
        cost = 5;
        effectType = EffectType.ON_EXERCISE;
        conditionList.add(PlayerCondition.squat);
    }

    @Override
    public float getEnergyMultiplier() {
        return energyValue;
    }
}
