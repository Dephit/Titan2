package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseActor;
import com.mygdx.game.PocketView;

public class Pocket extends Container {

    public int money;

    public void setMoney(int money) {
        this.money = money;
    }

    PocketView pocketView = new PocketView("pocket");

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


}
