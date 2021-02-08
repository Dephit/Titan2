package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class Player extends Npc {

    Player() {
        super("player");
        squat.setProgressAndCapacity(100, 50);
        squat.setBounds(50,50, 400, 400);
    }

    int currentGirlDialogProgress = 0;
    int coachDialogProgress = 0;

    StatBar squat = new StatBar("Присед");

    public void setHeavySquat() {
        setSquatExercise();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(playerCondition == PlayerCondition.squat){
            squat.updateProgress(delta);
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


}

