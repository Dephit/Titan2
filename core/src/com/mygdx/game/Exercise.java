package com.mygdx.game;

public class Exercise {

    public PlayerCondition condition;

    public Exercise(PlayerCondition statName) {
        condition = statName;
    }

    public float updateValue = 0.05f;
    public float updateEnergyValue = 0.05f;
    public float updateHealthValue = 0.05f;
    public float updateTirednessValue = 0.05f;
    public int result = 80;
    public int LVL = 0;
    public Boolean newLevelReached = false;

    int lvlMultiplier() {
        return 100 + LVL * 5;
    };

    public void setLVL(int LVL, boolean isReduced) {
        this.LVL = LVL;
        setResult();
        limit = lvlMultiplier();
        progress = isReduced ? limit : 0;
        newLevelReached = !isReduced;
    }

    public float progress = 0;
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
    }

    private void setResult() {
        result = lvlMultiplier() - 20;
    }
}