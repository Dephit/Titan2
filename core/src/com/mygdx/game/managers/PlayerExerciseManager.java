package com.mygdx.game.managers;

import com.mygdx.game.BenchPress;
import com.mygdx.game.Exercise;
import com.mygdx.game.Language;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Stat;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.items.Item;
import com.mygdx.game.model.items.supplements.SupplementItem;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayerExerciseManager extends ExerciseManager {

        private static final String SQUAT_EXERCISE = "SQUAT_EXERCISE";
        private static final String BENCH_EXERCISE = "SQUAT_EXERCISE";
        private static final String DEADLIFT_EXERCISE = "SQUAT_EXERCISE";
        private static final String PUSH_UP_EXERCISE = "SQUAT_EXERCISE";
        private static final String PULL_UPS_EXERCISE = "SQUAT_EXERCISE";

        private static final String HEALTH = "SQUAT_EXERCISE";
        private static final String ENERGY = "SQUAT_EXERCISE";
        private static final String TIREDNESS = "SQUAT_EXERCISE";

        Player player;

        public Exercise squatExr = new Exercise(PlayerCondition.squat);
        public Exercise bench = new BenchPress(PlayerCondition.bench);
        public Exercise deadlift = new Exercise(PlayerCondition.deadlift);
        Exercise pushUps = new Exercise(PlayerCondition.pushUps);
        Exercise pullUps = new Exercise(PlayerCondition.pullUps);

        public Stat health;
        public Stat energy;
        public Stat tiredness;

        public PlayerExerciseManager(Player player){
                this.player = player;
                health = new Stat();
                energy = new Stat();
                tiredness = new Stat();

                exercises.add(squatExr);
                exercises.add(bench);
                exercises.add(deadlift);
                exercises.add(pushUps);
                exercises.add(pullUps);

        }

        public void setDebugValues() {
                squatExr.setLVL(100, true);
                bench.setLVL(100,true);
                deadlift.setLVL(100,true);
        }

        public void act(float delta) {
                for(Exercise exercise: exercises){
                        if(exercise.condition == player.playerCondition){
                                calculateExercise(exercise);
                        }
                        if(exercise.newLevelReached){
                                player.inventoryManager.perkPocket.addMoney(1);
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
                                               value *= ((ContiniousItem) item).getExerciseMultiplier(player.playerCondition);
                                               updateEnergyValue *= ((ContiniousItem) item).getEnergyMultiplier();
                                               updateHealthValue *= ((ContiniousItem) item).getHealthMultiplier();
                                               updateTirednessValue *= ((ContiniousItem) item).getTirednessMultiplier();
                                       }
                                }
                        }

                        for (Item item: player.inventoryManager.perkContainer.getItems()) {
                                if(item instanceof ContiniousItem){
                                        if(((ContiniousItem) item).conditionList.contains(player.playerCondition)
                                                && ((ContiniousItem) item).effectType == EffectType.ON_EXERCISE){
                                                value *= ((ContiniousItem) item).getExerciseMultiplier(player.playerCondition);
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
                                                value *= ((ContiniousItem) item).getExerciseMultiplier(player.playerCondition);
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

        public JSONObject toJson(){
                JSONObject jsonObject = new JSONObject();
                try {
                        jsonObject.put(SQUAT_EXERCISE, squatExr.toJson());
                        jsonObject.put(BENCH_EXERCISE, bench.toJson());
                        jsonObject.put(DEADLIFT_EXERCISE, deadlift.toJson());
                        jsonObject.put(PUSH_UP_EXERCISE, pushUps.toJson());
                        jsonObject.put(PULL_UPS_EXERCISE, pullUps.toJson());

                        jsonObject.put(HEALTH, health.toJson());
                        jsonObject.put(ENERGY, energy.toJson());
                        jsonObject.put(TIREDNESS, tiredness.toJson());
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return jsonObject;
        }

        public void fromJson(JSONObject jsonObject){
                squatExr.fromJson(jsonObject);
                bench.fromJson(jsonObject);
                deadlift.fromJson(jsonObject);
                pushUps.fromJson(jsonObject);
                pullUps.fromJson(jsonObject);

                health.fromJson(jsonObject);
                energy.fromJson(jsonObject);
                tiredness.fromJson(jsonObject);
        }
}
