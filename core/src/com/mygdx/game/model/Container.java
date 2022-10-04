package com.mygdx.game.model;

import com.mygdx.game.model.items.Item;

import java.util.ArrayList;

public class Container {

    private ArrayList<Item> items = new ArrayList<>();

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


    public void removeItem(Item item){
        items.remove(item);
    }

    public boolean hasSpace() {
        return items.size() < totalCapacity;
    }
}

