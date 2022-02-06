package com.mygdx.game.model;


public class CompetitionOpponent{
    public String name;
    public int squatRes;
    public int benchRes;
    public int deadliftRes;

    public CompetitionOpponent(String name, int squatRes, int benchRes, int deadliftRes) {
        this.name = name;
        this.squatRes = squatRes;
        this.benchRes = benchRes;
        this.deadliftRes = deadliftRes;
    }
}