package com.mygdx.game.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;

        return message.contentEquals(that.message) && Objects.equals(showTime, that.showTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, showTime);
    }
}
