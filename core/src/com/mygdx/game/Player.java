package com.mygdx.game;

import com.mygdx.game.managers.InventoryManager;
import com.mygdx.game.managers.PlayerExerciseManager;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.Day;
import com.mygdx.game.model.items.Item;
import com.mygdx.game.managers.NotificationManager;
import com.mygdx.game.model.items.supplements.SupplementItem;

import org.json.JSONException;
import org.json.JSONObject;

public class Player extends Npc {

    private static final String DAY = "DAY";
    private static final String EXERCISE_MANAGER = "EXERCISE_MANAGER";
    private static final String INVENTORY_MANAGER = "INVENTORY_MANAGER";

    public NotificationManager notificationManager = new NotificationManager();
    public InventoryManager inventoryManager = new InventoryManager();
    public PlayerExerciseManager exerciseManager;
    public Day day = new Day();

    public CompetitionOpponent compValue;

    public Player() {
        super("player");
        exerciseManager = new PlayerExerciseManager(this);
        setDebugPlayer();
    }

    private void setDebugPlayer() {
        exerciseManager.setDebugValues();
        day.currentDay = 5;
    }

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
                exerciseManager.onPC();
                break;
        }
        exerciseManager.act(delta);
    }

    @Override
    public void onAnimationEnd() {
        log(playerCondition.name());
        if(playerCondition == PlayerCondition.compSquat || playerCondition == PlayerCondition.compBench || playerCondition == PlayerCondition.compDeadlift){
            setPlayerCondition(PlayerCondition.stay);
        }
    }

    private void calculateRecovery(float delta) {
        exerciseManager.calculateRecovery(delta);
    }

    @Override
    boolean isAnimationFinished() {
        boolean isFinished = super.isAnimationFinished();
        if(isFinished){
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
            day.addTime(this);
        }

        return isFinished;
    }

    public void buyItemToRefrigerator(Item item){
        if(!inventoryManager.buyItemToRefrigerator(item)){
            notificationManager.addMessage(getLanguage().refregiratorIsFull);
        }
    }

    public void buyItemToInventory(Item item){
        if(!inventoryManager.buyItemToInventory(item)){
            notificationManager.addMessage(getLanguage().refregiratorIsFull);
        }
    }

    public void buyEquipmentItem(ContiniousItem item){
        if(item instanceof SupplementItem){
            inventoryManager.buySupplement(this, item);
        }else if(!inventoryManager.buyItemToEquipment(this, item)){
            notificationManager.addMessage(getLanguage().refregiratorIsFull);
        }
    }

    public void setModerateSquat() {
        setSquatExercise();
    }

    public Exercise isInExercise() {
        return exerciseManager.isInExercise();
    }

    public void fromJson(JSONObject jsonObject){
        day.fromJson(jsonObject.optJSONObject(DAY));
        exerciseManager.fromJson(jsonObject.optJSONObject(EXERCISE_MANAGER));
        inventoryManager.fromJson(jsonObject.optJSONObject(INVENTORY_MANAGER));

    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(DAY, day.toJson());
            jsonObject.put(EXERCISE_MANAGER, exerciseManager.toJson());
            jsonObject.put(INVENTORY_MANAGER, inventoryManager.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

}

