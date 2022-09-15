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

    public float updateValue = 0.05f;
    public float updateEnergyValue = 0.05f;
    public float updateHealthValue = 0.05f;
    public float updateTirednessValue = 0.05f;
    public int result = 80;
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

    public void calculateProgress(float delta) {
        if(progress < 0){
            if(LVL > 0)
                setLVL(--LVL, true);
        }
        if(progress >= limit){
            setLVL(++LVL, false);
        }
        progress += delta;
        statBar.setProgress(progress);
    }

    private void setResult() {
        result = 80 + 20 * LVL;
    }
}