package com.mygdx.game;

public class BenchPress extends Exercise {

    public BenchPress(PlayerCondition statName) {
        super(statName);
    }


    @Override
    int lvlMultiplier() {
        return 100 + LVL * 3;
    }
}
