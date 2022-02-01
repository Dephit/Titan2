package com.mygdx.game;

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

    public void minusPrgress(float value){
        progress -=value;
        statBar.setProgress(progress);
    }

    public Float calculateProgress(float delta) {
        result = 80 + 20 * LVL;
        if(progress < 0){
            if(LVL > 0)
                LVL--;
            limit = 100 + LVL * 20;
            progress = 0;
        }
        if(progress > limit){
            LVL++;
            limit = 100 + LVL * 20;
            progress = 0;
        }
        progress += delta * 5f;
        statBar.setProgress(progress);
        return delta * 5f;
    }

    /*private float calculateByVariation(float v, float v1, float v2, float v3) {
        return variation == Variation.EASY ? v : variation == Variation.MODERATE ? v1 : variation == Variation.HEAVY ? v2 : v3;
    }*/
}