package com.mygdx.game.managers;

import com.mygdx.game.Player;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.EffectType;
import com.mygdx.game.model.EquipmentContainer;
import com.mygdx.game.model.Inventory;
import com.mygdx.game.model.PerkContainer;
import com.mygdx.game.model.Pocket;
import com.mygdx.game.model.Refrigerator;
import com.mygdx.game.model.SupplementsContainer;
import com.mygdx.game.model.items.Item;
import com.mygdx.game.model.items.perks.PerkItem;

import org.json.JSONException;
import org.json.JSONObject;

public class InventoryManager {

    private static final String INVENTORY = "INVENTORY";
    private static final String SUPPLEMENTS = "SUPPLEMENTS";
    private static final String PERKS = "PERKS";
    private static final String EQUIPMENTS = "EQUIPMENTS";
    private static final String REFRIGERATOR = "REFRIGERATOR";
    private static final String POCKET = "POCKET";
    private static final String PERK_POCKET = "PERK_POCKET";

    public Container inventory = new Inventory();
    public SupplementsContainer supplements = new SupplementsContainer();
    public PerkContainer perkContainer = new PerkContainer();
    public EquipmentContainer equipmentContainer = new EquipmentContainer();
    public Refrigerator refrigerator = new Refrigerator();
    public Pocket pocket = new Pocket(3000);
    public Pocket perkPocket = new Pocket(0);

    public boolean buyItemToRefrigerator(Item item) {
        if(refrigerator.hasSpace()){
            if(pocket.buy(item.cost)){
                refrigerator.addItem(item);
                return true;
            }else return false;
        }else return false;
    }

    public boolean buyItemToInventory(Item item) {
        if(inventory.hasSpace()){
            if(pocket.buy(item.cost)){
                inventory.addItem(item);
                return true;
            }else return false;
        } else return false;
    }

    public boolean buyItemToEquipment(Player player, ContiniousItem item) {
        if(equipmentContainer.hasSpace()){
            if(pocket.buy(item.cost)){
                for (Item equipItem: equipmentContainer.getItems()) {
                    if(((ContiniousItem) equipItem).type == item.type){
                        if (item.effectType == EffectType.WHILE_EQUIPPED){
                            item.onRemove(player);
                        }
                        equipmentContainer.removeItem(equipItem);
                        break;
                    }
                }
                equipmentContainer.addItem(item);
                if(item.effectType == EffectType.PERMANENT || item.effectType == EffectType.WHILE_EQUIPPED){
                    item.onUse(player);
                }
                return true;
            }else return false;
        } else return false;
    }

    public boolean buySupplement(Player player, ContiniousItem item) {
        if(supplements.hasSpace()){
            if(pocket.buy(item.cost)){
                for (Item equipItem: supplements.getItems()) {
                    if(((ContiniousItem) equipItem).type == item.type){
                        if (item.effectType == EffectType.WHILE_EQUIPPED){
                            item.onRemove(player);
                        }
                        supplements.removeItem(equipItem);
                        break;
                    }
                }
                supplements.addItem(item);
                if(item.effectType == EffectType.PERMANENT || item.effectType == EffectType.WHILE_EQUIPPED){
                    item.onUse(player);
                }
                return true;
            }else return false;
        } else return false;
    }

    public boolean hasPerk(PerkItem item) {
        return perkContainer.hasPerk(item);
    }

    public boolean hasPerk(String item) {
        return perkContainer.hasPerk(item);
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(INVENTORY, inventory.toJson());
            jsonObject.put(SUPPLEMENTS, supplements.toJson());
            jsonObject.put(PERKS, perkContainer.toJson());
            jsonObject.put(EQUIPMENTS, equipmentContainer.toJson());
            jsonObject.put(REFRIGERATOR, refrigerator.toJson());
            jsonObject.put(POCKET, pocket.toJson());
            jsonObject.put(PERK_POCKET, perkPocket.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject){
        inventory.fromJson(jsonObject.optJSONObject(INVENTORY));
        supplements.fromJson(jsonObject.optJSONObject(SUPPLEMENTS));
        perkContainer.fromJson(jsonObject.optJSONObject(PERKS));
        equipmentContainer.fromJson(jsonObject.optJSONObject(EQUIPMENTS));
        refrigerator.fromJson(jsonObject.optJSONObject(REFRIGERATOR));
        pocket.fromJson(jsonObject.optJSONObject(POCKET));
        perkPocket.fromJson(jsonObject.optJSONObject(PERK_POCKET));

    }
}
