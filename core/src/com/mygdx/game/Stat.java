package com.mygdx.game;

public class Stat {

    StatBar statBar;

    float value = 0;

    public Stat(String statName) {
        statBar = new StatBar(statName);
        statBar.setBounds(50,50, 600, 75);
    }

    public void minusProgress(float fatigue) {
        if(value > 0) {
            value -= fatigue;
        }
        statBar.setProgress(value);
    }

    public void addProgress(float fatigue) {
        if(value <= 100) {
            value += fatigue;
        }
        statBar.setProgress(value);
    }
}
