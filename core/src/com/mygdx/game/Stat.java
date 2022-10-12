package com.mygdx.game;

public class Stat {

    public boolean isZero = false;

    public float value = 100;

    public void minusProgress(float progress) {
        if(value > 0) {
            value -= progress;
        }
    }

    public void addProgress(float progress) {
        if(value <= 100) {
            value += progress;
        }
        isZero = false;
    }


    public float getCurrentAmount() {
        return value;
    }
}
