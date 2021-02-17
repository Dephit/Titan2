package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

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
        squat.setBounds(50,50, 600, 75);
    }

    int currentGirlDialogProgress = 0;
    int coachDialogProgress = 0;

    int squatResult = 80;
    float lastSquatResult = -1f;
    int squatLVL = 0;
    float squatLVLProgress = 0;
    float squatLVLLimit = 100;
    float fatigue = 100;
    float squatRecovery = 100;

    Variation variation;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(playerCondition == PlayerCondition.squat){
            calculateSquat(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(playerCondition == PlayerCondition.squat) {
            manageSquatText();
        };
    }

    private void manageSquatText() {
        String str = Preffics.getInstance().getLanguage().squat + ", ";
        str += Preffics.getInstance().getLanguage().level + " " + squatLVL + ", ";
        if(lastSquatResult != -1)
            str += Preffics.getInstance().getLanguage().bestResult + ": " + lastSquatResult;
        squat.drawText(str);
    }

    private void calculateSquat(float delta) {
        squatResult = 80 + 20 * squatLVL;
        if(squatLVLProgress > squatLVLLimit){
            squatLVL++;
            squatLVLLimit = 100 + squatLVL * 20;
            squatLVLProgress = 0;
        }
        if(squatRecovery <= 0){
            setPushUps();
            squatRecovery = fatigue;
            fatigue = 100;
            if(variation == Variation.MOCK){
                float random = 0.9f + new Random().nextFloat() * (0.95f - 0.9f);
                lastSquatResult = squatResult *= random;
            }
        }
        squatLVLProgress += delta * calculateByVariation(1.5f, 3.5f, 5.5f, 0.5f);
        fatigue -= delta * calculateByVariation(0.5f, 2.5f, 4.5f, 6.5f);
        squatRecovery -= delta * calculateByVariation(1.5f, 4.5f, 6.5f, 8.5f);

        squat.secondCurrentAmount = fatigue;
        squat.setProgress(squatRecovery);
    }

    private float calculateByVariation(float v, float v1, float v2, float v3) {
        return variation == Variation.EASY ? v : variation == Variation.MODERATE ? v1 : variation == Variation.HEAVY ? v2 : v3;
    }

    public void setHeavySquat() {
        variation = Variation.HEAVY;
        setSquatExercise();
    }

    public void setModerateSquat() {
        variation = Variation.MODERATE;
        setSquatExercise();
    }

    public void setEasySquat() {
        variation = Variation.EASY;
        setSquatExercise();
    }

    public void setMockSquat() {
        variation = Variation.MOCK;
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

