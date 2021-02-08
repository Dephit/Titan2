package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class Player extends Npc {

    StatBar squat = new StatBar("Присед");
    StatBar health = new StatBar("health");
    StatBar energy = new StatBar("energy");

    Player() {
        super("player");
        health.setBounds(1920 - 400 - 50, 1080 - 65 - 50, 400, 65);
        health.setProgressAndCapacity(100, 100);
        health.setColor(Color.RED);

        energy.setBounds(1920 - 400 - 50, 1080 - 65 * 2 - 25 - 50, 400, 65);
        energy.setProgressAndCapacity(100, 100);
        energy.setColor(Color.YELLOW);
        squat.setBounds(50,50, 400, 75);
    }

    int currentGirlDialogProgress = 0;
    int coachDialogProgress = 0;

    public void setHeavySquat() {
        setSquatExercise();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(playerCondition == PlayerCondition.squat){
            squat.updateProgress(delta * 5);
        }
    }

    public void setModerateSquat() {
        setSquatExercise();
    }

    public void setEasySquat() {
        setSquatExercise();
    }

    public void setMockSquat() {
        setSquatExercise();
    }

    StatBar getSquatBar(){
        return squat;
    }
    StatBar getHealthBar(){
        return health;
    }
    StatBar getEnergyBar(){
        return energy;
    }


}

