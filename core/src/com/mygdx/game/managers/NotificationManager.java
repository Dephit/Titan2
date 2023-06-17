package com.mygdx.game.managers;

import com.mygdx.game.Exercise;
import com.mygdx.game.Stat;
import com.mygdx.game.model.Notification;

import java.util.ArrayList;

public class NotificationManager {

    public NotificationManager() {

    }

    @Deprecated()
    //Addes testing messages used in testing
    private void addTestMessages() {
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

    public ArrayList<Notification> getNotificationList() {
        return notificationList;
    }

    private final ArrayList<Notification> notificationList = new ArrayList<>();

    public void countNotification() {
        try {
            for (Notification next : notificationList) {
                next.showTime -= 100;
                if (next.showTime <= 0) {
                    next.show = false;
                }
            }
        } catch (Exception ignored) {

        }
    }

    public void addNewLevelNotification(Exercise exercise) {
        notificationList.add(
            new Notification(
                exercise.condition + " is reached level " + exercise.LVL,
                10000L
            )
        );
        exercise.newLevelReached = false;
    }

    public void manageStats(Stat energy, Stat health) {
        if (energy.value <= 0 && !energy.isZero) {
            notificationList.add(new Notification("Your energy level is zero, go to sleep", 15000L));
            energy.isZero = true;
        }
        if (health.value <= 0 && !health.isZero) {
            notificationList.add(new Notification("Your too hungry to do this, go to eat", 15000L));
            health.isZero = true;
        }

    }

    public void addMessage(String msg) {
        Notification newNotification = new Notification(msg, 10000L);
        if(!notificationList.contains(newNotification)){
            notificationList.add(newNotification);
        }
    }
}
