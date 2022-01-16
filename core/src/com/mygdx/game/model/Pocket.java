package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseActor;
import com.mygdx.game.PocketView;

public class Pocket extends Container {

    int money;

    public void setMoney(int money) {
        this.money = money;
        updateView();
    }

    PocketView pocketView = new PocketView("pocket");

    public Pocket(int money){
        totalCapacity = 2;
        setMoney(money);
    }

    public PocketView getPocketView() {
        return pocketView;
    }

    private void updateView() {
        pocketView.setStr(String.valueOf(money));
    }

    public void addMoney(int amount){
        money += amount;
        updateView();
    }

    public boolean buy(int amount){
        if(amount <= money){
            money -= amount;
            updateView();
            return true;
        }else {
            return false;
        }
    }


}
