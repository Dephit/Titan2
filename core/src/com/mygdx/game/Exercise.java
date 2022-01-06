package com.mygdx.game;

import java.util.Random;

public class Exercise {

    PlayerCondition condition;
    public StatBar statBar;

    public Exercise(PlayerCondition statName) {
        condition = statName;
        statBar = new StatBar(statName.name());
        statBar.setProgressAndCapacity(100, (int) progress);;
        statBar.setBounds(50,50, 600, 75);
    }


    float recovery = 100;
    int result = 80;
    float lastResult = -1f;
    int LVL = 0;
    float progress = 0;
    float limit = 100;

    public Float calculateProgress(float delta) {
        result = 80 + 20 * LVL;
        if(progress > limit){
            LVL++;
            limit = 100 + LVL * 20;
            progress = 0;
        }
        /*if(recovery <= 0){
            setPushUps();
            recovery = fatigue;
            //fatigue = 100;
            *//*if(variation == Variation.MOCK){
                float random = 0.9f + new Random().nextFloat() * (0.95f - 0.9f);
                result = result *= random;
            }*//*
        }*/
        progress += delta * 5f;//calculateByVariation(1.5f, 3.5f, 5.5f, 0.5f);
        //fatigue -=
        //recovery -= delta * calculateByVariation(1.5f, 4.5f, 6.5f, 8.5f);

        //squat.secondCurrentAmount = fatigue;
        statBar.setProgress(progress);
        return delta * 5f;//calculateByVariation(0.5f, 2.5f, 4.5f, 6.5f);
    }

    /*private float calculateByVariation(float v, float v1, float v2, float v3) {
        return variation == Variation.EASY ? v : variation == Variation.MODERATE ? v1 : variation == Variation.HEAVY ? v2 : v3;
    }*/
}