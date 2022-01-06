package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.Random;

public class Player extends Npc {

    ArrayList<Exercise> exercises = new ArrayList();

    Exercise squatExr = new Exercise(PlayerCondition.squat);
    Exercise bench = new Exercise(PlayerCondition.bench);
    Exercise deadlift = new Exercise(PlayerCondition.deadlift);
    Exercise pushUps = new Exercise(PlayerCondition.pushUps);
    Exercise pullUps = new Exercise(PlayerCondition.pullUps);

    Stat health = new Stat("health");
    Stat energy = new Stat("energy");

    Player() {
        super("player");
        exercises.add(squatExr);
        exercises.add(bench);
        exercises.add(deadlift);
        exercises.add(pushUps);
        exercises.add(pullUps);
        health.statBar.setBounds(1920 - 400 - 50, 1080 - 65 - 50, 400, 65);
        health.statBar.setProgressAndCapacity(100, 100);
        health.statBar.setColor(Color.RED);

        energy.statBar.setBounds(1920 - 400 - 50, 1080 - 65 * 2 - 25 - 50, 400, 65);
        energy.statBar.setProgressAndCapacity(100, 100);
        energy.statBar.setColor(Color.YELLOW);
    }

    int currentGirlDialogProgress = 0;
    int coachDialogProgress = 0;

    Variation variation;

    @Override
    public void act(float delta) {
        super.act(delta);
        switch (playerCondition){
            case up:
            case down:
            case left:
            case right:
            case sitting:
            case sittingRev:
            case pcSitting:
            case lookinLeft:
            case lookinRight:
            case stay:
                calculateRecovery(delta);
                break;
        }
        for(Exercise exercise: exercises){
            if(exercise.condition == playerCondition){
                calculateExercise(exercise, delta);
            }
        }
    }

    private void calculateExercise(Exercise exr, float delta) {
        if(energy.value > 0){
            energy.minusProgress(exr.calculateProgress(delta));
        }else setPlayerCondition(PlayerCondition.stay);
    }

    private void calculateRecovery(float delta) {
        energy.addProgress(delta * 5f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(Exercise exercise: exercises){
            if(exercise.condition == playerCondition){
                manageText(exercise);
            }
        }
    }

    private void manageText(Exercise exercise) {
        String str = exercise.condition.name() + ", ";
        str += getLanguage().level + " " + exercise.LVL + ", ";
        if(exercise.lastResult != -1){
            str += getLanguage().bestResult + ": " + exercise.result;
        }
        exercise.statBar.drawText(str);
    }

    /*private void calculateSquat(float delta) {
        squatExr.result = 80 + 20 * squatExr.LVL;
        if(squatExr.progress > squatExr.limit){
            squatExr.LVL++;
            squatExr.limit = 100 + squatExr.LVL * 20;
            squatExr.progress = 0;
        }
        if(squatExr.recovery <= 0){
            setPushUps();
            squatExr.recovery = fatigue;
            //fatigue = 100;
            if(variation == Variation.MOCK){
                float random = 0.9f + new Random().nextFloat() * (0.95f - 0.9f);
                squatExr.result = squatExr.result *= random;
            }
        }
        squatExr.progress += delta * calculateByVariation(1.5f, 3.5f, 5.5f, 0.5f);
        fatigue -= delta * calculateByVariation(0.5f, 2.5f, 4.5f, 6.5f);
        squatExr.recovery -= delta * calculateByVariation(1.5f, 4.5f, 6.5f, 8.5f);

        energy.setProgress(fatigue);
        squat.secondCurrentAmount = fatigue;
        squat.setProgress(squatExr.recovery);
    }*/


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

    @Override
    void setPlayerCondition(PlayerCondition playerCondition) {
        clearExrercise();
        super.setPlayerCondition(playerCondition);
    }

    public void setMockSquat() {
        variation = Variation.MOCK;
        setSquatExercise();
    }

    StatBar getSquatBar(){
        return squatExr.statBar;
    }
    StatBar getHealthBar(){
        return health.statBar;
    }
    StatBar getEnergyBar(){
        return energy.statBar;
    }


    public Exercise isInExercise() {
        for(Exercise exercise: exercises){
            if(exercise.condition == playerCondition){
                return exercise;
            }
        }
        return null;
    }

    public void clearExrercise() {
        for(Exercise exercise: exercises){
            if(exercise.condition == playerCondition){
                exercise.statBar.remove();
            }
        }
    }
}

