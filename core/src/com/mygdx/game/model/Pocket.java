package com.mygdx.game.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Pocket extends Container {

    private static final String MONEY = "MONEY";
    public int money;

    public void setMoney(int money) {
        this.money = money;
    }

    public Pocket(int money){
        totalCapacity = 2;
        setMoney(money);
    }

    public void addMoney(int amount){
        money += amount;
    }

    public boolean hasEnough(int amount){
        return amount <= money;
    }

    public boolean buy(int amount){
        boolean has = hasEnough(amount);
        if(has){
            money -= amount;
        }
        return has;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = super.toJson();
        try {
            jsonObject.put(MONEY, money);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject){
        money = jsonObject.optInt(MONEY);
        super.fromJson(jsonObject);
    }

}
