package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.Group;

import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;

public class Container {

    private ArrayList<Item> items = new ArrayList<>();

    int totalCapacity = 0;

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

