package com.mygdx.game.managers;

import com.mygdx.game.BenchPress;
import com.mygdx.game.Exercise;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Stat;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.items.Item;

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

    private final Player player;

    public final Exercise squatExr = new Exercise(PlayerCondition.squat);
    public final Exercise bench = new BenchPress(PlayerCondition.bench);
    public final Exercise deadlift = new Exercise(PlayerCondition.deadlift);
    public final Exercise sleeping = new Exercise(PlayerCondition.sleeping);
    public final Exercise pushUps = new Exercise(PlayerCondition.pushUps);
    public final Exercise pullUps = new Exercise(PlayerCondition.pullUps);

    public final Stat health;
    public final  Stat energy;

    public PlayerExerciseManager(Player player) {
        this.player = player;
        health = new Stat();
        energy = new Stat();
        exercises.put(PlayerCondition.squat, squatExr);
        exercises.put(PlayerCondition.bench, bench);
        exercises.put(PlayerCondition.deadlift, deadlift);
        exercises.put(PlayerCondition.sleeping, sleeping);
        exercises.put(PlayerCondition.pushUps, pushUps);
        exercises.put(PlayerCondition.pullUps, pullUps);
    }

    @Deprecated
    public void setDebugValues() {
        squatExr.setLVL(100, true);
        bench.setLVL(100, true);
        deadlift.setLVL(100, true);
    }

    //TODO redo this logic
    public void act() {
        Exercise exercise = exercises.get(player.playerCondition);
        if(exercise != null) {
            calculateExercise(exercise);
            if (exercise.newLevelReached) {
                player.inventoryManager.perkPocket.addMoney(1);
                player.notificationManager.addNewLevelNotification(exercise);
            }
        }
    }

    private void calculateExercise(Exercise exr) {
        if(exr.condition == PlayerCondition.sleeping) {
            calculateRecovery();
            return;
        }
        if (energy.value > 0 && health.value > 0) {
            float value;
            float updateEnergyValue;
            float updateHealthValue;

            value = exr.updateValue;
            updateEnergyValue = exr.updateEnergyValue;
            updateHealthValue = exr.updateHealthValue;

            for (Item item : player.inventoryManager.equipmentContainer.getItems()) {
                if (item instanceof ContiniousItem) {
                    if (((ContiniousItem) item).conditionList.contains(player.playerCondition)
                        && ((ContiniousItem) item).effectType == EffectType.ON_EXERCISE) {
                        value *= ((ContiniousItem) item).getExerciseMultiplier(player.playerCondition);
                        updateEnergyValue *= ((ContiniousItem) item).getEnergyMultiplier();
                        updateHealthValue *= ((ContiniousItem) item).getHealthMultiplier();

                    }
                }
            }

            for (Item item : player.inventoryManager.perkContainer.getItems()) {
                if (item instanceof ContiniousItem) {
                    if (((ContiniousItem) item).conditionList.contains(player.playerCondition)
                        && ((ContiniousItem) item).effectType == EffectType.ON_EXERCISE) {
                        value *= ((ContiniousItem) item).getExerciseMultiplier(player.playerCondition);
                        updateEnergyValue *= ((ContiniousItem) item).getEnergyMultiplier();
                        updateHealthValue *= ((ContiniousItem) item).getHealthMultiplier();

                    }
                }
            }

            for (Item item : player.inventoryManager.supplements.getItems()) {
                if (item instanceof ContiniousItem) {
                    if (((ContiniousItem) item).conditionList.contains(player.playerCondition)
                        && ((ContiniousItem) item).effectType == EffectType.ON_EXERCISE) {
                        value *= ((ContiniousItem) item).getExerciseMultiplier(player.playerCondition);
                        updateEnergyValue *= ((ContiniousItem) item).getEnergyMultiplier();
                        updateHealthValue *= ((ContiniousItem) item).getHealthMultiplier();
                    }
                }
            }

            exr.calculateProgress(value);
            energy.minusProgress(updateEnergyValue);
            health.minusProgress(updateHealthValue);
        } else {
            player.notificationManager.manageStats(energy, health);
            player.setPlayerCondition(PlayerCondition.stay);
        }
    }

    public void calculateRecovery() {
        if (player.playerCondition != PlayerCondition.stay) {
            if (health.value > 0 && energy.value < 100) {
                energy.addProgress(0.1f);
                health.minusProgress(0.025f);
            } else {
                player.setPlayerPosition(550, 256, PlayerCondition.stay);
                //TODO make it only one message
                //player.postNotificationMessage("I don't want to sleep");
            }
        }
    }

    public Exercise isInExercise() {
        return exercises.get(player.playerCondition);
    }

    public void onWork() {
        health.minusProgress(2.5f);
        energy.minusProgress(5f);
        for (Exercise exercise : exercises.values()) {
            exercise.calculateProgress(-2.5f);
        }
    }

    public void onPark() {
        health.minusProgress(2.5f);
        energy.minusProgress(2.5f);
        //tiredness.addProgress(5f);
        for (Exercise exercise : exercises.values()) {
            exercise.calculateProgress(-2.5f);
        }
    }

    public void onPC() {
        float progress = 0.25f;
        health.minusProgress(progress * 0.25f);
        energy.minusProgress(progress * 0.35f);
        //tiredness.addProgress(progress * 0.15f);
        for (Exercise exercise : exercises.values()) {
            exercise.calculateProgress(-progress * 0.50f);
        }
    }

    public void addHealth(int progress) {
        health.addProgress(progress);
    }

    public void addEnergy(int progress) {
        energy.addProgress(progress);
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(SQUAT_EXERCISE, squatExr.toJson());
            jsonObject.putOpt(BENCH_EXERCISE, bench.toJson());
            jsonObject.putOpt(DEADLIFT_EXERCISE, deadlift.toJson());
            jsonObject.putOpt(PUSH_UP_EXERCISE, pushUps.toJson());
            jsonObject.putOpt(PULL_UPS_EXERCISE, pullUps.toJson());

            jsonObject.putOpt(HEALTH, health.toJson());
            jsonObject.putOpt(ENERGY, energy.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject) {
        squatExr.fromJson(jsonObject.optJSONObject(SQUAT_EXERCISE));
        bench.fromJson(jsonObject.optJSONObject(BENCH_EXERCISE));
        deadlift.fromJson(jsonObject.optJSONObject(DEADLIFT_EXERCISE));
        pushUps.fromJson(jsonObject.optJSONObject(PUSH_UP_EXERCISE));
        pullUps.fromJson(jsonObject.optJSONObject(PULL_UPS_EXERCISE));

        health.fromJson(jsonObject.optJSONObject(HEALTH));
        energy.fromJson(jsonObject.optJSONObject(ENERGY));
    }
}
