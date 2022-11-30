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
        private static final String BENCH_EXERCISE = "BENCH_EXERCISE";
        private static final String DEADLIFT_EXERCISE = "DEADLIFT_EXERCISE";
        private static final String PUSH_UP_EXERCISE = "PUSH_UP_EXERCISE";
        private static final String PULL_UPS_EXERCISE = "PULL_UPS_EXERCISE";

        private static final String HEALTH = "HEALTH";
        private static final String ENERGY = "ENERGY";
        private static final String TIREDNESS = "TIREDNESS";

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
                        energy.addProgress(0.1f);
                        health.minusProgress(0.025f);
                        tiredness.addProgress(0.0025f);
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
                health.minusProgress(2.5f);
                energy.minusProgress(5f);
                tiredness.minusProgress(2.5f);
                for (Exercise exercise: exercises){
                        exercise.calculateProgress(-2.5f);
                }
        }

        public void onPark() {
                health.minusProgress(2.5f);
                energy.minusProgress(2.5f);
                tiredness.addProgress(5f);
                for (Exercise exercise: exercises){
                        exercise.calculateProgress(-2.5f);
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
                        jsonObject.putOpt(SQUAT_EXERCISE, squatExr.toJson());
                        jsonObject.putOpt(BENCH_EXERCISE, bench.toJson());
                        jsonObject.putOpt(DEADLIFT_EXERCISE, deadlift.toJson());
                        jsonObject.putOpt(PUSH_UP_EXERCISE, pushUps.toJson());
                        jsonObject.putOpt(PULL_UPS_EXERCISE, pullUps.toJson());

                        jsonObject.putOpt(HEALTH, health.toJson());
                        jsonObject.putOpt(ENERGY, energy.toJson());
                        jsonObject.putOpt(TIREDNESS, tiredness.toJson());
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return jsonObject;
        }

        public void fromJson(JSONObject jsonObject){
                squatExr.fromJson(jsonObject.optJSONObject(SQUAT_EXERCISE));
                bench.fromJson(jsonObject.optJSONObject(BENCH_EXERCISE));
                deadlift.fromJson(jsonObject.optJSONObject(DEADLIFT_EXERCISE));
                pushUps.fromJson(jsonObject.optJSONObject(PUSH_UP_EXERCISE));
                pullUps.fromJson(jsonObject.optJSONObject(PULL_UPS_EXERCISE));

                health.fromJson(jsonObject.optJSONObject(HEALTH));
                energy.fromJson(jsonObject.optJSONObject(ENERGY));
                tiredness.fromJson(jsonObject.optJSONObject(TIREDNESS));
        }
}
