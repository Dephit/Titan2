package com.mygdx.game.model;

public class Pocket extends Container {

    int money;

    public Pocket(int money){
        totalCapacity = 2;
        this.money = money;
    }

    public void addMoney(int amount){
        money += amount;
    }

    public boolean buy(int amount){
        if(amount <= money){
            money -= amount;
            return true;
        }else {
            return false;
        }
    }
}
