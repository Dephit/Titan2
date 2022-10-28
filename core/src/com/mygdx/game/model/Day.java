package com.mygdx.game.model;

import com.mygdx.game.Player;
import com.mygdx.game.model.items.Item;
import com.mygdx.game.model.items.supplements.SupplementItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Day {

    private static final String CURRENT_DAY = "CURRENT_DAY";
    private static final String CURRENT_TIME = "CURRENT_TIME";

    public int currentDay = 0;
    public int currentTime = 0;

    public void addTime(Player player) {
        ArrayList<SupplementItem> items = new ArrayList<>();
        for (Item sup: player.inventoryManager.supplements.getItems()) {
            if(((ContiniousItem) sup).type == ItemType.SUPPLEMENT){
                ((SupplementItem) sup).timeInUseLeft ++;
                if(((SupplementItem) sup).timeInUseLeft >= ((SupplementItem) sup).timeWillBeLast){
                    items.add(((SupplementItem) sup));
                }
            }
        }
        for (SupplementItem i:items) {
            player.inventoryManager.supplements.removeItem(i);
        }
        currentTime++;
        if (currentTime > 30) {
            currentDay++;
            currentTime = 0;
        }
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(CURRENT_DAY, currentDay);
            jsonObject.put(CURRENT_TIME, currentTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject){
        currentDay = jsonObject.optInt(CURRENT_DAY);
        currentTime = jsonObject.optInt(CURRENT_TIME);
    }
}
