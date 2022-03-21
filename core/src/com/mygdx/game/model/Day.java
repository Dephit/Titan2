package com.mygdx.game.model;

public class Day {
    public int currentDay = 0;
    public int currentTime = 0;

    public void addTime() {
        currentTime++;
        if (currentTime > 30) {
            currentDay++;
            currentTime = 0;
        }
    }
}
