package com.mygdx.game.model;

import org.apache.commons.logging.Log;

import java.util.logging.Logger;

public class Refrigerator extends Container {

    public Refrigerator(){
        this.totalCapacity = 8;

        addItem(new Potato());
        addItem(new Potato());
        addItem(new Nuggets());
        addItem(new Potato());
        addItem(new Nuggets());
    }

}

