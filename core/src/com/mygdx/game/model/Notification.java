package com.mygdx.game.model;

public class Notification {

    public Notification(){

    }

    public Notification(String message){
        this.message = message;
    }

    public Notification(String message, Long showTime){
        this.message = message;
        this.showTime = showTime;
    }

    public String message;

    public Long showTime = 5000L;

    public Boolean show = true;
}
