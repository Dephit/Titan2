package com.mygdx.game.managers;

import com.mygdx.game.Exercise;
import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Stat;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.items.Item;
import com.mygdx.game.model.items.supplements.SupplementItem;

public class PlayerExerciseManager extends ExerciseManager {

        Player player;

        public Exercise squatExr = new Exercise(PlayerCondition.squat);
        public Exercise bench = new Exercise(PlayerCondition.bench);
        public Exercise deadlift = new Exercise(PlayerCondition.deadlift);
        Exercise pushUps = new Exercise(PlayerCondition.pushUps);
        Exercise pullUps = new Exercise(PlayerCondition.pullUps);

        public Stat health;
        public Stat energy;
        public Stat tiredness;

        public PlayerExerciseManager(Player player, Language language){
                this.player = player;
                health = new Stat(language.health);
                energy = new Stat(language.energy);
                tiredness = new Stat(language.moral);

                exercises.add(squatExr);
                exercises.add(bench);
                exercises.add(deadlift);
                exercises.add(pushUps);
                exercises.add(pullUps);

        }

        public void setDebugValues() {
                squatExr.setLVL(10, true);
                bench.setLVL(8,true);
                deadlift.setLVL(12,true);
        }

        public void act(float delta) {
                for(Exercise exercise: exercises){
                        if(exercise.condition == player.playerCondition){
                                calculateExercise(exercise);
                        }
                        if(exercise.newLevelReached){
                                player.notificationManager.addNewLevelNotification(exercise);
                        }
                }
        }

        private void calculateExercise(Exercise exr) {
                if(energy.value > 0 && health.value > 0){
                        float value;
                        float updateEnergyValue;
                        float updateHealthValue;
                        float updateTirednessValue;
                        value = exr.updateValue;
                        updateEnergyValue = exr.updateEnergyValue;
                        updateHealthValue = exr.updateHealthValue;
                        updateTirednessValue = exr.updateTirednessValue;
                        for (Item item: player.inventoryManager.equipmentContainer.getItems()) {
                                if(item instanceof ContiniousItem){
                                       if(((ContiniousItem) item).conditionList.contains(player.playerCondition)
                                               && ((ContiniousItem) item).effectType == EffectType.ON_EXERCISE){
                                               value *= ((ContiniousItem) item).getExerciseMultiplier();
                                               updateEnergyValue *= ((ContiniousItem) item).getEnergyMultiplier();
                                               updateHealthValue *= ((ContiniousItem) item).getHealthMultiplier();
                                               updateTirednessValue *= ((ContiniousItem) item).getTirednessMultiplier();
                                       }
                                }
                        }

                        for (Item item: player.inventoryManager.supplements.getItems()) {
                                if(item instanceof ContiniousItem){
                                        if(((ContiniousItem) item).conditionList.contains(player.playerCondition)
                                                && ((ContiniousItem) item).effectType == EffectType.ON_EXERCISE){
                                                value *= ((ContiniousItem) item).getExerciseMultiplier();
                                                updateEnergyValue *= ((ContiniousItem) item).getEnergyMultiplier();
                                                updateHealthValue *= ((ContiniousItem) item).getHealthMultiplier();
                                                updateTirednessValue *= ((ContiniousItem) item).getTirednessMultiplier();
                                        }
                                }
                        }

                        exr.calculateProgress(value);
                        energy.minusProgress(updateEnergyValue);
                        health.minusProgress(updateHealthValue);
                        tiredness.minusProgress(updateTirednessValue);
                }else {
                        player.notificationManager.manageStats(energy, health, tiredness);
                        player.setPlayerCondition(PlayerCondition.stay);
                }
        }

        public void calculateRecovery(float delta) {
                if(health.value > 0 && energy.value < 100){
                        energy.addProgress(delta * 5f);
                        health.minusProgress(delta * 5f );
                }else player.setPlayerCondition(PlayerCondition.stay);
        }

        public Exercise isInExercise() {
                for(Exercise exercise: exercises){
                        if(exercise.condition == player.playerCondition){
                                return exercise;
                        }
                }
                return null;
        }

        public void clearExercise() {
                for(Exercise exercise: exercises){
                        if(exercise.condition == player.playerCondition){
                                exercise.statBar.remove();
                        }
                }
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

        public void addHealth(int progress) {
                health.addProgress(progress);
        }

        public void addEnergy(int progress) {
                energy.addProgress(progress);
        }

        public int getBestSquat() {
                return squatExr.result;
        }
}
