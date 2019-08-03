package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

class PlayerStats {
    private final Preferences prefs;
    Array<StatBar> mainStats = new Array<>();
    Array<StatBar> stats = new Array<>();
    Array<StatBar> statsScreenStats = new Array<>();

    int nuggetsAmount = 1;
    int potatoAmount = 3;
    int inventoryCap = 0, invetoryMaxCap = 4;
    int refrigiratorCap = 0, refrigiratorMaxCap = 8;

    StatBar energy, food, stress;

    StatBar legStrenght, backStrenght, squatTechnic, benchTechnic, deadliftTechnic, gripStrenght, archStrenght, armStrenght;

    float squatRes = 0, benchRes = 0, deadliftRes = 0;

    private float leanBodyWeight = 68, fat = 10, bodyWeight = 74;

    float chanceToCompliteExr = 100;

    int money;

    private float D9 = 0, E9 = 0, F9 = 0;

    Map<String, Object> data;

    enum CompetitionEvent{
        weightIn, watchSquatTable, squatFirst, orderSecSquat, squatSecond, orderThirdSquat, squatThird,
                  watchBenchTable, benchFirst, orderSecBench, benchSecond, orderThirdBench, benchThird,
                  watchDeadliftTable, deadliftFirst, orderSecDeadlift, deadliftSecond, orderThirdDeadlift, DeadliftThird,
        watchResultTab, ceremony
    }

    CompetitionEvent competitionEvent = CompetitionEvent.weightIn;

    PlayerStats() {
        prefs = Gdx.app.getPreferences("My Preferences");

        legStrenght = new StatBar("Сила ног",10,10,116 * 2,19 * 2, 0, 10);
        backStrenght = new StatBar("Сила спины",10,10,116 * 2,19 * 2, 0, 10);
        squatTechnic = new StatBar("Присед техника",10,10,116 * 2,19 * 2, 0, 10);
        archStrenght = new StatBar("Жимовой мост",10,10,116 * 2,19 * 2, 0, 10);
        armStrenght = new StatBar("Сила рук",10,10,116 * 2,19 * 2, 0, 10);
        deadliftTechnic = new StatBar("Тяга техника",10,10,116 * 2,19 * 2, 0, 10);
        benchTechnic = new StatBar("Жим техника",10,10,116 * 2,19 * 2, 0, 10);
        gripStrenght = new StatBar("Сила хвата",10,10,116 * 2,19 * 2, 0, 10);

        energy = new StatBar("Энергия", 1630, 830, 116 * 1.5f, 19 * 1.5f);
        food = new StatBar("Еда", 1630, 790, 116 * 1.5f, 19 * 1.5f);
        stress = new StatBar("Уровень стресса", 1630, 870, 116 * 1.5f, 19 * 1.5f);

        energy.setShowAlways(true);
        energy.setColor(Color.YELLOW);


        food.setShowAlways(true);
        food.setColor(Color.GREEN);


        stress.setShowAlways(true);
        stress.setColor(Color.RED);
        setStats();

        mainStats.add(energy);
        mainStats.add(food);
        mainStats.add(stress);

        stats.add(legStrenght);
        stats.add(backStrenght);
        stats.add(squatTechnic);
        stats.add(archStrenght);
        stats.add(armStrenght);
        stats.add(benchTechnic);
        stats.add(deadliftTechnic);
        stats.add(gripStrenght);

    }

    void setStats() {
        money = 99999;

        for (StatBar stat : stats) {
            stat.setCurrentAmount(0);
            stat.setLeveled(true,0,0);
        }


        energy.setCurrentAmount(1f);


        food.setCurrentAmount(1f);


        stress.setCurrentAmount(1f);
    }

    void calcRes(){
        leanBodyWeight = bodyWeight*(1 - fat / 100);
        squatRes = 50 + ((legStrenght.getLevel() * 1.5f + backStrenght.getLevel() + squatTechnic.getLevel() * 0.5f) / 3 * 17.5f) * squatMult() * (leanBodyWeight / 100);
        benchRes = 50 + (15 * (armStrenght.getLevel() * 1.75f + archStrenght.getLevel() + benchTechnic.getLevel() * 0.75f * 0.5f ) / 3) * benchMult() * (leanBodyWeight / 100);
        deadliftRes = 50 + (( backStrenght.getLevel() * 1.5f + legStrenght.getLevel() + deadliftTechnic.getLevel() * 0.5f) / 3 * 22.5f) * deadliftMult() * (leanBodyWeight / 100);
    }

    void save(IActivityRequestHandler myRequestHandler){
        Map<String, Object> data = new HashMap<>();
        for (StatBar stat : stats) {
            data.putAll(stat.save(prefs));
        }
        for (StatBar stat : mainStats) {
            data.putAll(stat.save(prefs));
        }
        data.put("nuggetsAmount", nuggetsAmount);
        prefs.putInteger("nuggetsAmount", nuggetsAmount);

        data.put("potatoAmount", potatoAmount);
        prefs.putInteger("potatoAmount", potatoAmount);

        data.put("money", money);
        prefs.putInteger("money", money);

        myRequestHandler.saveData(data);
        prefs.flush();
    }

    void load() {
        for (StatBar stat : stats) {
            stat.load(prefs);
        }
        for (StatBar stat : mainStats) {
            stat.load(prefs);
        }

        nuggetsAmount = prefs.getInteger("nuggetsAmount", 1);
        potatoAmount = prefs.getInteger("potatoAmount", 3);
        money = prefs.getInteger("money", 99999);
    }

    void loadFromCloud(IActivityRequestHandler myRequestHandler) {
        data = myRequestHandler.loadData();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while(data == null){
                    data = myRequestHandler.loadData();
                }

                try {
                    System.out.println("Получилось" + data);
                    for (StatBar stat : stats) {
                        stat.loadFromCloud(data);
                    }
                    for (StatBar stat : mainStats) {
                        stat.loadFromCloud(data);
                    }
                    nuggetsAmount =  ((Long) data.get("nuggetsAmount")).intValue();
                    potatoAmount =  ((Long) data.get("potatoAmount")).intValue();
                    money =  ((Long) data.get("money")).intValue();
                }catch (NullPointerException e){
                    System.out.println("Не получилось");
                }
            }
        };
        task.run();
        //Thread thread = new Thread(task);
        //thread.start();

        //TODO Make this task to process the delay
    }

    private float deadliftMult() {
        float F7 = weightClassMuld("deadlift");
        float F8 = 0.3f - (0.03f * leanBodyWeight / 140) + F9/100;
        return (1 + F8 ) * F7;
    }

    private float weightClassMuld(String exr) {
        if (leanBodyWeight <= 59)
            return exr.equals("squat") ? 1.175f : exr.equals("bench") ? 1.05f : 1.16f;
        if (leanBodyWeight <= 66)
            return exr.equals("squat") ? 1.2f : exr.equals("bench") ? 1 : 1.165f;
        if (leanBodyWeight <= 74)
            return exr.equals("squat") ? 1.175f : exr.equals("bench") ? 1 : 1.15f;
        if (leanBodyWeight <= 83)
            return exr.equals("squat") ? 1.16f : exr.equals("bench") ? 1 : 1.105f;
        if (leanBodyWeight <= 93)
            return exr.equals("squat") ? 1.15f : exr.equals("bench") ? 0.947f : 1.07f;
        if (leanBodyWeight <= 105)
            return exr.equals("squat") ? 1.115f : exr.equals("bench") ? 0.919f : 1.02f;
        if (leanBodyWeight <= 120)
            return exr.equals("squat") ? 1.1f : exr.equals("bench") ? 0.95f : 0.9f;
        return exr.equals("squat") ? 1.06f : exr.equals("bench") ? 0.815f : 0.87f;
    }

    private float benchMult() {
        float E7 = weightClassMuld("bench");
        float E8 = 0.3f - (0.03f * leanBodyWeight / 140) + E9 / 100;
        return (1 + E8) * E7;
    }

    private float squatMult() {
        float D7 = weightClassMuld("squat");
        float D8 = 0.3f - (0.03f * leanBodyWeight / 140) + D9/100;
        return (1 + D8) * D7;
    }

    void showBars(Batch batch){
        int i = 0;
        for (StatBar child : stats) {
            child.setY( 10 + i * 70);
            if (child.isVisible()) {
                MyMethods.textDrawing(batch, child.getName(), child.getX(), child.getY() + 60, 1, Color.WHITE);
                MyMethods.textDrawing(batch, "Ур. " + child.getLevel(), child.getX() + child.getWidth() + 10, child.getY() + child.getHeight() / 2, 1, Color.CYAN);
            }
            i += child.isVisible() ? 1 : 0;
        }
    }

    void showBarsOnStatScreen(Batch batch){
        int i = 0;
        MyMethods.textDrawing(batch, "Присед: " + squatRes + " Жим: " + benchRes + " Тяга: " + deadliftRes + " Вес: " + (squatRes + benchRes + deadliftRes)+ " Вес: " + bodyWeight, 500, 900, 1, Color.CYAN);
        MyMethods.textDrawing(batch, "Деньги: " + money, 500, 840, 1, Color.CYAN);
        for (StatBar child : stats) {
            child.timer = 98;
            child.setY( 10 + i * 70);
            MyMethods.textDrawing(batch, child.getName(), child.getX(), child.getY() + 60, 1, Color.CYAN);
            MyMethods.textDrawing(batch, "Ур. " + child.getLevel(), child.getX() + child.getWidth() + 10, child.getY() + child.getHeight() / 2, 1, Color.CYAN);
            i += child.isVisible() ? 1 : 0;
        }
    }
}
