package com.mygdx.game;

public class Stat {

    StatBar statBar;

    float value = 100;

    public Stat(String statName) {
        statBar = new StatBar(statName);
        statBar.setBounds(50,50, 600, 75);
    }

    public void minusProgress(float progress) {
        if(value > 0) {
            value -= progress;
        }
        statBar.setProgress(value);
    }

    public void addProgress(float progress) {
        if(value <= 100) {
            value += progress;
        }
        statBar.setProgress(value);
    }
}
