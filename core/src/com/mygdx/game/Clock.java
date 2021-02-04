package com.mygdx.game;

/*
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class Clock {

    private final StatBar days;
    private final StatBar minutes;
    private final StatBar hours;
    private final StatBar hoursSecond;
    private final StatBar minutesSecond;
    private final ArrayList<StatBar> stats;

    private float time;

    public Clock() {

        days=new StatBar("День", 0,0,0,0);
        days.setLeveled(true, 1,1000);
        days.makeLevelTextDrawning(true, 1825,990,1.3f, Color.WHITE);
        days.setShowAlways(true);

        minutes=new StatBar("Минута", 0,0,0,0);
        minutes.setLeveled(true,0,5);
        minutes.makeLevelTextDrawning(true,1820,950,1f,Color.WHITE);
        minutes.setShowAlways(true);

        minutesSecond=new StatBar("Минута2", 0,0,0,0);
        minutesSecond.setLeveled(true,0,9);
        minutesSecond.makeLevelTextDrawning(true,1840,950,1f,Color.WHITE);
        minutesSecond.setShowAlways(true);

        hours=new StatBar("Час", 0,0,0,0);
        hours.setLeveled(true,1,2);
        hours.makeLevelTextDrawning(true,1775,950,1f,Color.WHITE);
        hours.setShowAlways(true);

        hoursSecond=new StatBar("Час2", 0,0,0,0);
        hoursSecond.setLeveled(true,2,9);
        hoursSecond.makeLevelTextDrawning(true,1790,950,1f,Color.WHITE);
        hoursSecond.setShowAlways(true);

        stats = new ArrayList<>();
        stats.add(days);
        stats.add(minutes);
        stats.add(hours);
        stats.add(hoursSecond);
        stats.add(minutesSecond);
    }

    void calculateHours() {
        time+=1f;
        if(time>=50){
            time=0;
            minutesSecond.setLeveled(true,minutesSecond.getLevel() + 1, minutesSecond.getMaxLevel());
            if(minutesSecond.getLevel() > minutesSecond.getMaxLevel()){
                minutesSecond.setLeveled(true,0, minutesSecond.getMaxLevel());
                minutes.setLeveled(true,minutes.getLevel() + 1,minutes.getMaxLevel());
            }
            if(minutes.getLevel() > minutes.getMaxLevel()){
                minutes.setLeveled(true,0,minutes.getMaxLevel());
                hoursSecond.setLeveled(true,hoursSecond.getLevel()+1,hoursSecond.getMaxLevel());
            }
            if(hoursSecond.getLevel()>hoursSecond.getMaxLevel()){
                hoursSecond.setLeveled(true,0,hoursSecond.getMaxLevel());
                hours.setLeveled(true,hours.getLevel()+1,hours.getMaxLevel());
            }
            if(hours.getLevel() == hours.getMaxLevel() && hoursSecond.getLevel() > 3){
                minutesSecond.setLeveled(true,0,minutesSecond.getMaxLevel());
                minutes.setLeveled(true,0,minutes.getMaxLevel());
                hoursSecond.setLeveled(true,0,hoursSecond.getMaxLevel());
                hours.setLeveled(true,0,hours.getMaxLevel());
                days.setLeveled(true,days.getLevel()+1,days.getMaxLevel());
            }
        }
    }

    public ArrayList<StatBar> getClock() {
        return  stats;
    }
}
*/
