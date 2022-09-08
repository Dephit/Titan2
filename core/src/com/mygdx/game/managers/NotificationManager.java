package com.mygdx.game.managers;

import com.mygdx.game.Exercise;
import com.mygdx.game.model.Notification;

import java.util.ArrayList;
import java.util.Iterator;

public class NotificationManager {

    public NotificationManager(){
        Notification not1 = new Notification();
        not1.showTime = 10000L;
        not1.message = "message 1";

        Notification not2 = new Notification();
        not2.showTime = 5000L;
        not2.message = "message 2";

        Notification not3 = new Notification();
        not3.showTime = 15000L;
        not3.message = "message 3";
        notificationList.add(not1);
        notificationList.add(not2);
        notificationList.add(not3);
    }

    public ArrayList<Notification> notificationList = new ArrayList();

    public void countNotification(){
        for (Notification next : notificationList) {
            next.showTime -= 100;
            if (next.showTime <= 0) {
                //notificationList.remove(next);
                next.show = false;
            }
        }
    }

    public void addNewLevelNotification(Exercise exercise) {
        Notification n = new Notification();
        n.showTime = 10000L;
        n.message = exercise.condition + " is reached level " + exercise.LVL;
        notificationList.add(n);
        exercise.newLevelReached = false;
    }
}
