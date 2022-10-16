package com.mygdx.game.model;

import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.model.items.Item;

import java.util.ArrayList;

public class ContiniousItem extends Item {


    public ItemType type;

    public EffectType effectType;
    public Float exerciseValue = 1.2f;
    public Float energyValue = 1f;

    public ArrayList<PlayerCondition> conditionList = new ArrayList<>();

    public String getPercentages(Float exerciseValue) {
        return Math.round((exerciseValue > 1 ? (exerciseValue - 1) * 100 : exerciseValue * 100f)) + "%";
    }

    public float getExerciseMultiplier(PlayerCondition playerCondition) {
        return 1.0f;
    }

    public float getEnergyMultiplier() {
        return 1.0f;
    }

    public float getHealthMultiplier() {
        return 1.0f;
    }

    public float getTirednessMultiplier() {
        return 1.0f;
    }

    public void onRemove(Player player) {

    }
}

