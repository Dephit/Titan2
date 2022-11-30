package com.mygdx.game.model;

import com.google.gson.Gson;
import com.mygdx.game.model.items.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class Container {

    private static final String ITEMS = "ITEMS";
    private final ArrayList<Item> items = new ArrayList<>();

    public int totalCapacity = 0;

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean addItem(Item item){
        if(hasSpace()){
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean addItems(Item... items){
        if(hasSpace(items.length)){
            Collections.addAll(this.items, items);
            return true;
        }
        return false;
    }


    public void removeItem(Item item){
        items.remove(item);
    }

    public boolean hasSpace() {
        return items.size() < totalCapacity;
    }

    public boolean hasSpace(int amount) {
        return items.size() + amount < totalCapacity;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Item item: items) {
            try {
                JSONObject obj = new JSONObject(new Gson().toJson(item));
                obj.putOpt("TYPE", item.getClass().getCanonicalName());
                System.out.println(item.getClass().getCanonicalName());
                jsonArray.put(obj);
            }catch (Exception e){

            }
        }
        try {
            jsonObject.put(ITEMS, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject){
        JSONArray jsonArray = jsonObject.optJSONArray(ITEMS);
        items.clear();
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject ogj = jsonArray.optJSONObject(i);
                Class c = Class.forName(ogj.getString("TYPE"));
                items.add(new Gson().fromJson(ogj.toString(), (Type) c));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

