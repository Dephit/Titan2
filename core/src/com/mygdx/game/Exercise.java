package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class Exercise {

    public PlayerCondition condition;
    public StatBar statBar;

    public Exercise(PlayerCondition statName) {
        condition = statName;
        statBar = new StatBar(statName.name());
        statBar.setProgressAndCapacity(100, (int) progress);;
        statBar.setBounds(50,50, 600, 75);
        statBar.setColor(Color.valueOf("ef7b45"));
        statBar.setBackgroundColor(Color.valueOf("042a2b"));
        statBar.setTextColor(Color.WHITE);
    }


    float recovery = 100;
    int result = 80;
    float lastResult = -1f;
    public int LVL = 0;
    public Boolean newLevelReached = false;

    public void setLVL(int LVL, boolean isReduced) {
        this.LVL = LVL;
        setResult();
        limit = 100 + LVL * 20;
        statBar.setCapacity((int) limit);
        progress = isReduced ? limit : 0;
        newLevelReached = !isReduced;
    }

    float progress = 0;
    public float limit = 100;

    public void minusPrgress(float value){
        progress -=value;
        statBar.setProgress(progress);
    }

    public Float calculateProgress(float delta) {
        if(progress < 0){
            if(LVL > 0)
                setLVL(LVL--, true);
        }
        if(progress >= limit){
            setLVL(LVL++, false);
        }
        progress += delta * 25f;
        statBar.setProgress(progress);
        return delta * 5f;
    }

    private void setResult() {
        result = 80 + 20 * LVL;
    }

    /*private float calculateByVariation(float v, float v1, float v2, float v3) {
        return variation == Variation.EASY ? v : variation == Variation.MODERATE ? v1 : variation == Variation.HEAVY ? v2 : v3;
    }*/
}