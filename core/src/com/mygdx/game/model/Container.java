package com.mygdx.game.model;

import com.mygdx.game.model.items.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Container {

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
}

