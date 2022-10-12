package com.mygdx.game.model;


public class Pocket extends Container {

    public int money;

    public void setMoney(int money) {
        this.money = money;
    }

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
