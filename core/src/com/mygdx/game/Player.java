package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Player extends Npc {

    Player() {
        super("player");
    }

    int currentGirlDialogProgress = 0;
    int coachDialogProgress = 0;


    public void setHeavySquat() {
        setSquatExercise();
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
}

