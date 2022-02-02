package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.Item;
import com.mygdx.game.model.Pocket;
import com.mygdx.game.model.Refrigerator;

import java.util.ArrayList;
import java.util.Random;

public class Player extends Npc {

    ArrayList<Exercise> exercises = new ArrayList();

    Exercise squatExr = new Exercise(PlayerCondition.squat);
    Exercise bench = new Exercise(PlayerCondition.bench);
    Exercise deadlift = new Exercise(PlayerCondition.deadlift);
    Exercise pushUps = new Exercise(PlayerCondition.pushUps);
    Exercise pullUps = new Exercise(PlayerCondition.pullUps);

    Stat health;
    Stat energy;
    Stat moral;

    public Refrigerator refrigerator = new Refrigerator();
    public Pocket pocket = new Pocket(75);

    Player(Language language) {
        super("player");
        health = new Stat(language.health);
        energy = new Stat(language.energy);
        moral = new Stat(language.moral);

        exercises.add(squatExr);
        exercises.add(bench);
        exercises.add(deadlift);
        exercises.add(pushUps);
        exercises.add(pullUps);

        health.statBar.setBounds(1920 - 400 - 50, 1080 - 65 - 50, 400, 65);
        health.statBar.setProgressAndCapacity(100, 100);
        health.statBar.setColor(Color.valueOf("910101"));
        health.statBar.setTextColor(Color.WHITE);
        health.statBar.setBackgroundColor(Color.valueOf("042a2b"));

        energy.statBar.setBounds(1920 - 400 - 50, 1080 - 65 * 2 - 25 - 50, 400, 65);
        energy.statBar.setProgressAndCapacity(100, 100);
        energy.statBar.setColor(Color.valueOf("AE9C00"));
        energy.statBar.setTextColor(Color.WHITE);
        energy.statBar.setBackgroundColor(Color.valueOf("042a2b"));

        moral.statBar.setBounds(1920 - 400 - 50, 1080 - 65 * 3 - 50 - 50, 400, 65);
        moral.statBar.setProgressAndCapacity(100, 100);
        moral.statBar.setTextColor(Color.WHITE);
        moral.statBar.setColor(Color.valueOf("240953"));
        moral.statBar.setBackgroundColor(Color.valueOf("042a2b"));

        pocket.getPocketView().setBounds(1920 - 410 - 50, 1080 - 65 * 4 - 25 - 100 - 15, 400, 65);
    }

    public int currentGirlDialogProgress = 0;
    public int coachDialogProgress = 0;

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
            case lookinLeft:
            case lookinRight:
            case stay:
                break;
            case sleeping:
                calculateRecovery(delta);
                break;
            case pcSitting:
                onPC();
                break;
        }
        for(Exercise exercise: exercises){
            if(exercise.condition == playerCondition){
                calculateExercise(exercise, delta);
            }
        }
    }

    @Override
    public void onAnimationEnd() {
        log(playerCondition.name());
        if(playerCondition == PlayerCondition.compSquat || playerCondition == PlayerCondition.compBench || playerCondition == PlayerCondition.compDeadlift){
            setPlayerCondition(PlayerCondition.stay);
        }
    }

    private void calculateExercise(Exercise exr, float delta) {
        if(energy.value > 0 && health.value > 0){
            Float value = exr.calculateProgress(delta);
            energy.minusProgress(value);
            health.minusProgress(value / 2);
            moral.minusProgress(value / 4);
        }else setPlayerCondition(PlayerCondition.stay);
    }

    private void calculateRecovery(float delta) {
        energy.addProgress(delta * 5f);
        health.minusProgress(delta * 5f );
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

    public boolean buyItem(Item item, Container container){
        if(pocket.buy(item.cost) && container.hasSpace()){
            refrigerator.addItem(item);
            return true;
        }else return false;
    }

    public void setModerateSquat() {
        setSquatExercise();
    }

    @Override
    void setPlayerCondition(PlayerCondition playerCondition) {
        clearExrercise();
        super.setPlayerCondition(playerCondition);
    }

    public StatBar getHealthBar(){
        return health.statBar;
    }

    public StatBar getEnergyBar(){
        return energy.statBar;
    }

    public StatBar getMoralBar(){
        return moral.statBar;
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

    public void addHealth(int i) {
        health.addProgress(i);
    }

    public Actor getPocketView() {
        return pocket.getPocketView();
    }

    public void onWork() {
        float progress = 0.5f;
        health.minusProgress(progress * 0.5f);
        energy.minusProgress(progress);
        moral.minusProgress(progress);
        for (Exercise exercise: exercises){
            exercise.calculateProgress(-progress * 0.50f);
        }
    }

    public void onPark() {
        float progress = 0.5f;
        health.minusProgress(progress * 0.5f);
        energy.minusProgress(progress * 0.25f);
        moral.addProgress(progress * 0.25f);
        for (Exercise exercise: exercises){
            exercise.calculateProgress(-progress * 0.50f);
        }
    }

    public void onPC() {
        float progress = 0.25f;
        health.minusProgress(progress * 0.25f);
        energy.minusProgress(progress * 0.35f);
        moral.addProgress(progress * 0.15f);
        for (Exercise exercise: exercises){
            exercise.calculateProgress(-progress * 0.50f);
        }
    }
}

