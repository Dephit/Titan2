package com.mygdx.game;

import org.json.JSONException;
import org.json.JSONObject;

public class Stat {

    private static final String VALUE = "VALUE";
    private static final String IS_ZERO = "IS_ZERO";

    public boolean isZero = false;

    public float value = 100;

    public void minusProgress(float progress) {
        if(value > 0) {
            value -= progress;
        }
    }

    public void addProgress(float progress) {
        if(value <= 100) {
            value += progress;
        }
        isZero = false;
    }


    public float getCurrentAmount() {
        return value;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(VALUE, value);
            jsonObject.put(IS_ZERO, isZero);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject){
        value = jsonObject.optInt(VALUE);
        isZero = jsonObject.optBoolean(IS_ZERO);
    }
}
