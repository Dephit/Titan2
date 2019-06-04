package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import static com.mygdx.game.MyMethods.format;
import static com.mygdx.game.MyMethods.textDrawing;
import static com.mygdx.game.Player.PlayerCondition.archWorkout;
import static com.mygdx.game.Player.PlayerCondition.bench;
import static com.mygdx.game.Player.PlayerCondition.benchTechnic;
import static com.mygdx.game.Player.PlayerCondition.deadlift;
import static com.mygdx.game.Player.PlayerCondition.deadliftTechnic;
import static com.mygdx.game.Player.PlayerCondition.down;
import static com.mygdx.game.Player.PlayerCondition.gripWorkout;
import static com.mygdx.game.Player.PlayerCondition.left;
import static com.mygdx.game.Player.PlayerCondition.right;
import static com.mygdx.game.Player.PlayerCondition.sleeping;
import static com.mygdx.game.Player.PlayerCondition.squat;
import static com.mygdx.game.Player.PlayerCondition.squatTechnic;
import static com.mygdx.game.Player.PlayerCondition.stay;
import static com.mygdx.game.Player.PlayerCondition.talkToArmGirl;
import static com.mygdx.game.Player.PlayerCondition.up;
import static com.mygdx.game.Player.PlayerCondition.working;

public class Player extends Actor {
    private final StatBar days;
    private final StatBar minutes;
    private final StatBar hours;
    private final StatBar hoursSecond;
    private final StatBar minutesSecond;
    private final Animation walk;
    private final Animation walkRev;
    private StatBar workProgress;
    public  StatBar energy, food;
    public  StatBar health;
    private StatBar legStrenght;
    private StatBar armStrenght;
    private StatBar backStrenght;
    private StatBar grip;
    private StatBar arch;
    private StatBar squatTechnique;
    private StatBar benchTechnique;
    private StatBar deadliftTechnique;
    public  ArrayList<StatBar> stats;
    public  ArrayList<StatBar> showedStats;

    private float squat_f;
    private float bench_f;
    private float deadlift_f;
    private Animation animation;
    private Vector2 speed;
    private float body_lean_mass;
    private float squat_multiply;
    private float bench_multiply;
    private float deadlift_multiply;
    private float body_weight;
    private float fat_level;
    private float money;
    private float time;

    float at = 0;
    private Animation stayPos;
    private TextureRegion currentFrame = new TextureRegion();
    private Animation upA;
    private Animation downA;

    public enum PlayerCondition {
        stay, down, up, left, right, squat, bench, deadlift,squatTechnic,
        benchTechnic, deadliftTechnic, gripWorkout, archWorkout, sleeping, working, talkToArmGirl
    }

    public PlayerCondition getPlayerCondition() {
        return playerCondition;
    }

    public PlayerCondition getNextPlayerCondition() {
        return nextPlayerCondition;
    }

    private PlayerCondition playerCondition, nextPlayerCondition;
    private float animationTime,nextX,nextY;

    Player() {
        setName("player");
        speed=new Vector2();

        energy = new StatBar("Энергия", 1640,785,150,150);
        energy.makeRadian(new Color(240/255f,230/255f,74/255f,1));
        food = new StatBar("Еда", 1660f,805f,110,110);
        food.makeRadian(new Color(0/255f,255/255f,100/255f,1));
        health = new StatBar("Здоровье", 1675,820,80,80);
        health.makeRadian(new Color(220/255f,20/255f,60/255f,1));

        workProgress=new StatBar("Работа", 150,0,300,40);

        legStrenght = new StatBar("Сила ног", 310,950,79*5,17*5);
        legStrenght.setLeveled(true,0,10);

        armStrenght = new StatBar("Сила рук", 620,950,79*5,17*5);
        armStrenght.setLeveled(true,0,10);

        backStrenght= new StatBar("Сила спины", 930,950,79*5,17*5);
        backStrenght.setLeveled(true,0,10);

        grip = new StatBar("Хват",310,900,79*5,17*5);
        grip.setLeveled(true,0,10);

        arch = new StatBar("Мост", 620,900,79*5,17*5);
        arch.setLeveled(true,0,10);

        squatTechnique = new StatBar("Техника приседа", 930,900,79*5,17*5);
        squatTechnique.setLeveled(true,0,10);

        benchTechnique = new StatBar("Техника жима", 310,850,79*5,17*5);
        benchTechnique.setLeveled(true,0,10);

        deadliftTechnique = new StatBar("Техника тяги", 930,850,79*5,17*5);
        deadliftTechnique.setLeveled(true,0,10);

        days=new StatBar("День", 0,0,0,0);
        days.setLeveled(true, 1,1000);
        days.makeLevelTextDrawning(true, 1825,990,1.3f,Color.WHITE);
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

        energy.setCurrentAmount(1f);
        health.setCurrentAmount(1f);
        workProgress.setCurrentAmount(0);

        stats = new ArrayList();
        showedStats=new ArrayList<StatBar>();

        stats.add(energy);
        stats.add(food);
        stats.add(health);
       // stats.add(workProgress);
        stats.add(legStrenght);
        stats.add(armStrenght);
        stats.add(backStrenght);
        stats.add(grip);
        stats.add(arch);
        stats.add(squatTechnique);
        stats.add(benchTechnique);
        stats.add(deadliftTechnique);
        stats.add(days);
        stats.add(minutes);
        stats.add(hours);
        stats.add(hoursSecond);
        stats.add(minutesSecond);

        squat_f=100;
        bench_f=100;
        deadlift_f=100;

        setParameters();
        walk=MyMethods.createAnimation("walking.png",10,1,1f);
        walkRev=MyMethods.createAnimation("walkingR.png",10,1,1f);
        stayPos=MyMethods.createAnimation("hero.png",1,1,1f);
        animation=MyMethods.createAnimation("player.png",36,1,1f);
        upA=MyMethods.createAnimation("up.png",8,1,1f);
        downA=MyMethods.createAnimation("down.png",6,1,1f);
    }

    void setParameters() {
        setBounds(400,100,145,245);

        animationTime=0;
        fat_level=10;
        playerCondition=stay;
        setName("player");
        nextPlayerCondition=stay;
        nextX=0;
        nextY=0;
        body_weight=74;
        body_lean_mass=body_weight*(1-fat_level/100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);


        if(playerCondition == right ) {

          //  setBounds(getX(), getY(), 145/5*5.3f, 255/5*5.3f);
            at += parentAlpha / 3.5f; // #15
            if(at <0f || at >10f) at = 0;

            currentFrame = (TextureRegion) walk.getKeyFrame(at, true);

        }else  if(playerCondition == up ) {

            //  setBounds(getX(), getY(), 145/5*5.3f, 255/5*5.3f);
            at += parentAlpha / 3.5f; // #15
            if(at <0f || at >7f) at = 0;

            currentFrame = (TextureRegion) upA.getKeyFrame(at, true);

        }else  if(playerCondition == down ) {

            //  setBounds(getX(), getY(), 145/5*5.3f, 255/5*5.3f);
            at += parentAlpha / (3.5f * 1.75f); // #15
            if(at < 0f || at > 5f) at = 0;

            currentFrame = (TextureRegion) downA.getKeyFrame(at, true);

        }else   if(playerCondition == left ) {

          //  setBounds(getX(), getY(), 145/5*5.3f, 255/5*5.3f);
            at += parentAlpha / 3.5f; // #15
            if(at <0f || at >10f) at = 0;

            currentFrame = (TextureRegion) walkRev.getKeyFrame(at, true);

        } else if(playerCondition == stay ) {
          //  setBounds(getX(),getY(),110,275);

            at += parentAlpha / 3.5f; // #15
            if(at <0f || at >10f) at = 0;
            currentFrame = (TextureRegion) stayPos.getKeyFrame(at, true);

        } else {
           // setBounds(getX(),getY(),110,275);

            currentFrame = (TextureRegion) animation.getKeyFrame(animationTime, true);
        }
        batch.draw(currentFrame,getX()-getWidth()/2,getY()/*-getHeight()*0.25f*/,getWidth(),getHeight());
        }

    @Override
    public void act(float delta) {
        super.act(delta);
        playAnimation(delta);
        changePlayerCondition();
       //calculateStats();
        Walk();
        calculateHours();
    }

    public void setPlayerCondition(PlayerCondition playerCondition) {
        this.playerCondition = playerCondition;
    }

    private void calculateHours() {
        time+=1f;
        if(time>=50){
            time=0;
            minutesSecond.setLeveled(true,minutesSecond.getLevel() + 1, minutesSecond.getMaxLevel());
            if(minutesSecond.getLevel() > minutesSecond.getMaxLevel()){
                minutesSecond.setLeveled(true,0, minutesSecond.getMaxLevel());
                minutes.setLeveled(true,minutes.getLevel() + 1,minutes.getMaxLevel());
            }
            if(minutes.getLevel()>minutes.getMaxLevel()){
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

    private void calculateStats() {
        squat_f=50 + ((legStrenght.getLevel() * 1.5f + backStrenght.getLevel() + squatTechnique.getLevel() * 0.5f) / 3 * 17.5f) * squat_multiply * (body_lean_mass / 100);
        bench_f=50 + (15 * (armStrenght.getLevel() * 1.75f + arch.getLevel() + benchTechnique.getLevel() * 0.75f * 0.5f) / 3) * bench_multiply * (body_lean_mass / 100);
        deadlift_f=50 + ((backStrenght.getLevel() * 1.5f + legStrenght.getLevel() + deadliftTechnique.getLevel() * 0.5f) / 3 * 22.5f) * deadlift_multiply * (body_lean_mass / 100);

        squat_multiply= (1 + (0.3f - (0.03f * body_lean_mass / 140))) * get_squat_cat_coef();
        bench_multiply= (1 + (0.3f - (0.03f * body_lean_mass / 140))) * get_bench_cat_coef();
        deadlift_multiply= (1 + (0.3f - (0.03f * body_lean_mass / 140))) * get_deadlift_cat_coef();
        body_weight=body_lean_mass+fat_level;

        if(energy.getCurrentAmount()>0&&health.getCurrentAmount()>0){
            if(playerCondition == squat){
                countCurrAmount(new SimpleEntry[]{
                        new SimpleEntry<StatBar, Float>(energy, -2.5f),
                        new SimpleEntry<StatBar, Float>(health, -1f),
                        new SimpleEntry<StatBar, Float>(legStrenght, 5f),
                        new SimpleEntry<StatBar, Float>(backStrenght, 2.5f),
                        new SimpleEntry<StatBar, Float>(squatTechnique, 1f)});
            }
            else if(playerCondition == bench){
                countCurrAmount(new SimpleEntry[]{
                        new SimpleEntry<StatBar, Float>(energy, -2.5f),
                        new SimpleEntry<StatBar, Float>(health, -1f),
                        new SimpleEntry<StatBar, Float>(armStrenght, 5f),
                        new SimpleEntry<StatBar, Float>(arch, 0.5f),
                        new SimpleEntry<StatBar, Float>(benchTechnique, 1f)});
            }
            else if(playerCondition == deadlift){
                countCurrAmount(new SimpleEntry[]{
                        new SimpleEntry<StatBar, Float>(energy, -2.5f),
                        new SimpleEntry<StatBar, Float>(health, -1f),
                        new SimpleEntry<StatBar, Float>(legStrenght, 2.5f),
                        new SimpleEntry<StatBar, Float>(backStrenght, 5f),
                        new SimpleEntry<StatBar, Float>(deadliftTechnique, 1f),
                        new SimpleEntry<StatBar, Float>(grip, 0.5f) });
            }
            else if(playerCondition == squatTechnic){
                countCurrAmount(new SimpleEntry[]{
                        new SimpleEntry<StatBar, Float>(energy, -1f),
                        new SimpleEntry<StatBar, Float>(health, -0.5f),
                        new SimpleEntry<StatBar, Float>(legStrenght, 0.5f),
                        new SimpleEntry<StatBar, Float>(backStrenght, 2.5f),
                        new SimpleEntry<StatBar, Float>(squatTechnique, 5f)});
            }
            else if(playerCondition == benchTechnic){
                countCurrAmount(new SimpleEntry[]{
                        new SimpleEntry<StatBar, Float>(energy, -1f),
                        new SimpleEntry<StatBar, Float>(health, -0.5f),
                        new SimpleEntry<StatBar, Float>(armStrenght, 0.5f),
                        new SimpleEntry<StatBar, Float>(arch, 0.5f),
                        new SimpleEntry<StatBar, Float>(benchTechnique, 5f)});
            }
            else if(playerCondition == deadliftTechnic){
                countCurrAmount(new SimpleEntry[]{
                        new SimpleEntry<StatBar, Float>(legStrenght, 0.25f),
                        new SimpleEntry<StatBar, Float>(backStrenght, 0.5f),
                        new SimpleEntry<StatBar, Float>(deadliftTechnique, 5f),
                        new SimpleEntry<StatBar, Float>(grip, 0.5f),
                        new SimpleEntry<StatBar, Float>(energy, -1f),
                        new SimpleEntry<StatBar, Float>(health, -0.5f)});
                }
            else if(playerCondition == gripWorkout){
                countCurrAmount(new SimpleEntry[]{
                        new SimpleEntry<StatBar, Float>(energy, -2.5f),
                        new SimpleEntry<StatBar, Float>(health, -0.5f),
                        new SimpleEntry<StatBar, Float>(grip, 5f)});
            }
            else if(playerCondition == archWorkout){
                countCurrAmount(new SimpleEntry[]{
                        new SimpleEntry<StatBar, Float>(energy, -2.5f),
                        new SimpleEntry<StatBar, Float>(health, -0.5f),
                        new SimpleEntry<StatBar, Float>(arch, 5f)});

            }else if(playerCondition == working){
                countCurrAmount(new SimpleEntry[]{
                        new SimpleEntry<StatBar, Float>(workProgress, 10f),
                        new SimpleEntry<StatBar, Float>(energy, -5f),
                        new SimpleEntry<StatBar, Float>(health, -2.5f),
                        new SimpleEntry<StatBar, Float>(legStrenght, -2f),
                        new SimpleEntry<StatBar, Float>(backStrenght, -2f),
                        new SimpleEntry<StatBar, Float>(armStrenght, -2f)});
            }
        }

        if(playerCondition==sleeping){
            countCurrAmount(new SimpleEntry[]{
                    new SimpleEntry<StatBar, Float>(energy, 100f),
                    new SimpleEntry<StatBar, Float>(health, 100f),
                    new SimpleEntry<StatBar, Float>(legStrenght, -150f),
                    new SimpleEntry<StatBar, Float>(legStrenght, -100f),
                    new SimpleEntry<StatBar, Float>(legStrenght, -150f)});

            if(food.getCurrentAmount()>=0.75f){
                body_lean_mass+=0.5f;
                fat_level+=0.75f;
            }else  if(food.getCurrentAmount()>=0.5f){
                body_lean_mass+=0.25f;
                fat_level+=0.25f;
            }else  if(food.getCurrentAmount()<0.5f){
                body_lean_mass-=0.25f;
                fat_level-=0.5f;
            }
            food.setCurrentAmount(0f);
        }
    }

    private void countCurrAmount(AbstractMap.SimpleEntry<StatBar, Float>[] pair) {
        int i=0;
        for (AbstractMap.SimpleEntry<StatBar, Float> entry : pair) {
            entry.getKey().addProgress(entry.getValue()/100f);
            if(entry.getKey().isLeveled()){
                entry.getKey().setPosition(0,0+entry.getKey().getHeight()*i);
                i++;
            }
            entry.getKey().setVisible(true);

        }
    }

    private float get_bench_cat_coef(){
        float res;
        if (body_lean_mass<=59) res=1.05f;
        else if(body_lean_mass<=66) res=1f;
        else if(body_lean_mass<=74) res=1f;
        else if(body_lean_mass<=83) res=0.99f;
        else if (body_lean_mass<=93) res=0.994f;
        else if (body_lean_mass<=105)res=0.9f;
        else if (body_lean_mass<=120)res=0.95f;
        else res=0.8f;
        return res;
    }

    private float get_deadlift_cat_coef(){
        float res;
        if (body_lean_mass<=59) res=1.16f;
        else if(body_lean_mass<=66) res=1.165f;
        else if(body_lean_mass<=74) res=1.15f;
        else if(body_lean_mass<=83) res=1.1f;
        else if (body_lean_mass<=93) res=1.07f;
        else if (body_lean_mass<=105)res=1.02f;
        else if (body_lean_mass<=120)res=0.9f;
        else res=0.85f;
        return res;
    }

    private float get_squat_cat_coef(){
        float res;
        if (body_lean_mass<=59) res=1.175f;
        else if(body_lean_mass<=66) res=1.2f;
        else if(body_lean_mass<=74) res=1.175f;
        else if(body_lean_mass<=83) res=1.15f;
        else if (body_lean_mass<=93) res=1.125f;
        else if (body_lean_mass<=105)res=1.1f;
        else if (body_lean_mass<=120)res=1;
        else res=1.05f;
        return res;
    }

    private void changePlayerCondition() {
        if(MyGdxGame.path!=null)
            if(MyGdxGame.path.isEmpty()){
                playerCondition=nextPlayerCondition;

                if(nextX>0||nextY>0){
                    setPosition(nextX,nextY);
                }
            }
            else if(speed.x>0)playerCondition=right;
            else if(speed.x<0)playerCondition=left;
            else if(speed.y>0)playerCondition=up;
            else if(speed.y<0)playerCondition=down;
    }

    private void playAnimation(float delta){
        if(playerCondition==down){

            animationTime += delta*10;
            if(animationTime <8f || animationTime >11f) animationTime =8f;

        }else
        if(playerCondition==up){

            animationTime +=delta*10;
            if(animationTime <2f || animationTime >5f ) animationTime =2f;

        }else
        if(playerCondition==left){

            animationTime +=delta*10;
            if(animationTime <16f || animationTime >19f) animationTime =16f;

        }else
        if(playerCondition==right){

            animationTime += delta*10; // #15
            if(animationTime <12f || animationTime >15f) animationTime =12f;

        }else
            if(playerCondition==stay){

            animationTime +=delta;
            if(animationTime <6|| animationTime >7f){
                calculateStats();
                animationTime =6f;
            }

        }else
            if(playerCondition==squat||playerCondition==squatTechnic){

            animationTime +=delta*5;
            if(animationTime <19|| animationTime >23f) {
                calculateStats();
                animationTime =19f;
            }

        }else
            if(playerCondition==bench||playerCondition==benchTechnic||playerCondition == archWorkout){

            animationTime +=delta*5;
            if(animationTime <23|| animationTime >27f){
                calculateStats();
                animationTime =23f;
            }

        }else
            if(playerCondition==deadlift||playerCondition==deadliftTechnic||playerCondition==gripWorkout){

            animationTime +=delta*5;
            if(animationTime <28|| animationTime >32f){
                calculateStats();
                animationTime =28f;
            }
        }
        else if(playerCondition==sleeping){
            animationTime +=delta;
            if(animationTime <33){
                setBounds(0,0,1920,1080);
               // batch.draw(currentFrame,getX()-getWidth()/2,getY()/*-getHeight()*0.25f*/,getWidth(),getHeight());
                animationTime =33f;
            }else
            if(animationTime >36f){
                calculateStats();
                setPlayersAction(stay,400,100);
                setBounds(400,100,140,220);
            }
        }
        else if(playerCondition==working){
            if(workProgress.getCurrentAmount()>=1f || energy.getCurrentAmount()<=0 || health.getCurrentAmount()<=0){
                if(workProgress.getCurrentAmount()>=1f)money+=1500;
                setPlayersAction(stay,400,100);
                setBounds(400,100,140,220);
            }
            animationTime +=delta;
        }
        else if(playerCondition==talkToArmGirl) {
            this.getStage().addActor(new Message(800,700,"Пошел ты!", false));
            setPlayersAction(stay,955,450);
        }
    }

    public void setPlayersAction(PlayerCondition playerCondition, int x, int y) {
        this.nextPlayerCondition = playerCondition;
        nextX=x;
        nextY=y;
        setStatVisible();
    }

    public void setStatVisible() {
        for (StatBar stat : stats) {
            if(stat.isLeveled() && !stat.isShowAlways())
                stat.setVisible(false);
            else
                stat.setVisible(true);
        }
    }

    private void Walk() {
        setX(getX() + speed.x);
        setY(getY() + speed.y);
        speed.setZero();
        if(MyGdxGame.path!=null)
            if(!MyGdxGame.path.isEmpty()) {
                  if (MyGdxGame.path.get(0).x > getX() / MyGdxGame.mapCoorinateCorrector) {
                    speed.x = 5f;
                } if (MyGdxGame.path.get(0).x < getX() / MyGdxGame.mapCoorinateCorrector) {
                    speed.x = -5f;
                } if (MyGdxGame.path.get(0).y > getY() / MyGdxGame.mapCoorinateCorrector) {
                    speed.y = 5f;
                } if (MyGdxGame.path.get(0).y < getY() / MyGdxGame.mapCoorinateCorrector) {
                    speed.y = -5f;
                }
                if (MyGdxGame.path.get(0).x == (int) getX() / MyGdxGame.mapCoorinateCorrector && MyGdxGame.path.get(0).y == (int) getY() / MyGdxGame.mapCoorinateCorrector) {
                    MyGdxGame.path.remove(0);
                }
            }
    }
}
