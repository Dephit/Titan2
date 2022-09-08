package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.managers.ExerciseManager;
import com.mygdx.game.managers.PlayerExerciseManager;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.Day;
import com.mygdx.game.model.Item;
import com.mygdx.game.managers.NotificationManager;
import com.mygdx.game.model.Notification;
import com.mygdx.game.model.Pocket;
import com.mygdx.game.model.Refrigerator;

import java.util.ArrayList;

public class Player extends Npc {

    public NotificationManager notificationManager = new NotificationManager();
    public PlayerExerciseManager exerciseManager = new PlayerExerciseManager();

    public Day day = new Day();

    public CompetitionOpponent compValue;

    ArrayList<Exercise> exercises = new ArrayList();

    Exercise squatExr = new Exercise(PlayerCondition.squat);
    Exercise bench = new Exercise(PlayerCondition.bench);
    Exercise deadlift = new Exercise(PlayerCondition.deadlift);
    Exercise pushUps = new Exercise(PlayerCondition.pushUps);
    Exercise pullUps = new Exercise(PlayerCondition.pullUps);

    Stat health;
    Stat energy;
    Stat tiredness;

    public Refrigerator refrigerator = new Refrigerator();
    public Pocket pocket = new Pocket(75);

    public Player(Language language) {
        super("player");
        health = new Stat(language.health);
        energy = new Stat(language.energy);
        tiredness = new Stat(language.moral);

        setDebugPlayer();

        exercises.add(squatExr);
        exercises.add(bench);
        exercises.add(deadlift);
        exercises.add(pushUps);
        exercises.add(pullUps);

        /*health.statBar.setBounds(1920 - 400 - 50, 1080 - 65 - 50, 400, 65);
        health.statBar.setProgressAndCapacity(100, 100);
        health.statBar.setColor(Color.valueOf("910101"));
        health.statBar.setTextColor(Color.WHITE);
        health.statBar.setBackgroundColor(Color.valueOf("042a2b"));

        energy.statBar.setBounds(1920 - 400 - 50, 1080 - 65 * 2 - 25 - 50, 400, 65);
        energy.statBar.setProgressAndCapacity(100, 100);
        energy.statBar.setColor(Color.valueOf("AE9C00"));
        energy.statBar.setTextColor(Color.WHITE);
        energy.statBar.setBackgroundColor(Color.valueOf("042a2b"));

        tiredness.statBar.setBounds(1920 - 400 - 50, 1080 - 65 * 3 - 50 - 50, 400, 65);
        tiredness.statBar.setProgressAndCapacity(100, 100);
        tiredness.statBar.setTextColor(Color.WHITE);
        tiredness.statBar.setColor(Color.valueOf("240953"));
        tiredness.statBar.setBackgroundColor(Color.valueOf("042a2b"));*/

        pocket.getPocketView().setBounds(1920 - 410 - 50, 1080 - 65 * 4 - 25 - 100 - 15, 400, 65);
    }

    private void setDebugPlayer() {
        squatExr.setLVL(10, true);
        bench.setLVL(8,true);
        deadlift.setLVL(12,true);
        day.currentDay = 5;
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
            if(exercise.newLevelReached){
                notificationManager.addNewLevelNotification(exercise);
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
            tiredness.minusProgress(value / 4);
        }else {
            notificationManager.manageStats(energy, health, tiredness);
            setPlayerCondition(PlayerCondition.stay);
        }
    }

    private void calculateRecovery(float delta) {
        if(health.value > 0 && energy.value < 100){
            energy.addProgress(delta * 5f);
            health.minusProgress(delta * 5f );
        }else setPlayerCondition(PlayerCondition.stay);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(Exercise exercise: exercises){
            if(exercise.condition == playerCondition){
                manageText(exercise);
            }
        }
        showText(batch,day.currentDay + " " + day.currentTime, 1920 - 410 - 50, 1080 - 65 * 6 - 25 - 100 - 15, 10, Color.BLACK );
    }

    @Override
    boolean isAnimationFinished() {
        boolean isFinishd = super.isAnimationFinished();
        if(isFinishd){
            if(playerCondition != PlayerCondition.stay && playerCondition != PlayerCondition.compSquat
                    && playerCondition != PlayerCondition.compDeadlift
                    && playerCondition != PlayerCondition.compBench
                    && playerCondition != PlayerCondition.lookinUp
                    && playerCondition != PlayerCondition.sittingRev
                    && playerCondition != PlayerCondition.sitting
                    && playerCondition != PlayerCondition.lookinLeft
                    && playerCondition != PlayerCondition.lookinRight
                    && playerCondition != PlayerCondition.watchLoli
                    && playerCondition != PlayerCondition.watchCam
                    && playerCondition != PlayerCondition.watchShop
                    && playerCondition != PlayerCondition.up
                    && playerCondition != PlayerCondition.left
                    && playerCondition != PlayerCondition.right
                    && playerCondition != PlayerCondition.down)
            day.addTime();
        }

        return isFinishd;
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

    public StatBar getTirednessBar(){
        return tiredness.statBar;
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
        float progress = 0.5f * 10;
        health.minusProgress(progress * 0.5f);
        energy.minusProgress(progress);
        tiredness.minusProgress(progress);
        for (Exercise exercise: exercises){
            exercise.calculateProgress(-progress * 0.50f);
        }
    }

    public void onPark() {
        float progress = 0.5f;
        health.minusProgress(progress * 0.5f);
        energy.minusProgress(progress * 0.25f);
        tiredness.addProgress(progress);
        for (Exercise exercise: exercises){
            exercise.calculateProgress(-progress * 0.50f);
        }
    }

    public void onPC() {
        float progress = 0.25f;
        health.minusProgress(progress * 0.25f);
        energy.minusProgress(progress * 0.35f);
        tiredness.addProgress(progress * 0.15f);
        for (Exercise exercise: exercises){
            exercise.calculateProgress(-progress * 0.50f);
        }
    }

    public int getSquatLvl() {
        return squatExr.LVL;
    }

    public int getBenchLvl() {
        return bench.LVL;
    }

    public int getDlLvl() {
        return deadlift.LVL;
    }

    public int getBestSquat() {
        return squatExr.result;
    }

    public int getBestBench() {
        return bench.result;
    }

    public int getBestDeadlift() {
        return deadlift.result;
    }


}

