package com.mygdx.game;

import org.json.JSONException;
import org.json.JSONObject;

public class Exercise {

    private static final String RESULT = "RESULT";
    private static final String PROGRESS = "PROGRESS";
    private static final String LVL_VALUE = "LVL_VALUE";
    private static final String NEW_LEVEL_REACHED = "NEW_LEVEL_REACHED";

    public PlayerCondition condition;

    public Exercise(PlayerCondition statName) {
        condition = statName;
    }

    public float updateValue = 0.1f;
    public float updateEnergyValue = 0.025f;
    public float updateHealthValue = 0.0125f;
    public float updateTirednessValue = 0.0075f;

    public int bestResult = 80;
    public int LVL = 0;
    public Boolean newLevelReached = false;

    int lvlMultiplier() {
        return 100 + LVL * 5;
    };

    public void setLVL(int LVL, boolean isReduced) {
        this.LVL = LVL;
        setResult();
        limit = lvlMultiplier();
        progress = isReduced ? limit : 0;
        newLevelReached = !isReduced;
    }

    public float progress = 0;
    public float limit = 100;

    public void calculateProgress(float delta) {
        if(progress < 0){
            if(LVL > 0)
                setLVL(--LVL, true);
        }
        if(progress >= limit){
            setLVL(++LVL, false);
        }
        progress += delta;
    }

    private void setResult() {
        bestResult = lvlMultiplier() - 20;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(RESULT, bestResult);
            jsonObject.put(LVL_VALUE, LVL);
            jsonObject.put(PROGRESS, progress);
            jsonObject.put(NEW_LEVEL_REACHED, newLevelReached);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject){
        bestResult = jsonObject.optInt(RESULT, 80);
        LVL = jsonObject.optInt(LVL_VALUE, 0);
        progress = jsonObject.optInt(PROGRESS, 0);
        newLevelReached = jsonObject.optBoolean(NEW_LEVEL_REACHED, false);
    }
}