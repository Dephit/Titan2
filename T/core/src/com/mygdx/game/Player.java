package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import static com.mygdx.game.MyMethods.getJson;
import static com.mygdx.game.MyMethods.getPath;
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
import static com.mygdx.game.Player.PlayerCondition.talkToCoach;
import static com.mygdx.game.Player.PlayerCondition.talkToArmGirl;
import static com.mygdx.game.Player.PlayerCondition.talkToBicepsGuy;
import static com.mygdx.game.Player.PlayerCondition.up;
import static com.mygdx.game.Player.PlayerCondition.working;

public class Player extends Actor {
    private final StatBar days;
    private final StatBar minutes;
    private final StatBar hours;
    private final StatBar hoursSecond;
    private final StatBar minutesSecond;

    private final TextureAtlas textureAtlas;

    private final ArrayList<Message> messages = new ArrayList<>();

    private StatBar workProgress;
    StatBar energy, food;
    StatBar health;
    private StatBar legStrenght;
    private StatBar armStrenght;
    private StatBar backStrenght;
    private StatBar grip;
    private StatBar arch;
    private StatBar squatTechnique;
    private StatBar benchTechnique;
    private StatBar deadliftTechnique;
    ArrayList<StatBar> stats;

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

    private float animationTime;
    private Animation stayPos;
    private TextureRegion currentFrame = new TextureRegion();
    private Animation upA;
    private Animation downA;
    private Animation squatA;
    private Animation deadliftA;

    private Map<String, Animation<TextureRegion>> aniimList;
    Vector2 lastWalkeblePosition = new Vector2();
    public int sizeMult = 5;

    public enum PlayerCondition {
        //Walk
        stay, down, up, left, right,
        //Exercise
        squat, bench, deadlift, squatTechnic, pullUps,
        benchTechnic, deadliftTechnic, gripWorkout, archWorkout, legPress, hiper, pushups,
        //Other
        sleeping, working, sitting, sittingRev,
        //Dialog Triger
        talkToArmGirl, talkToBicepsGuy, talkToCoach
    }



    public PlayerCondition getNextPlayerCondition() {
        return nextPlayerCondition;
    }

    private PlayerCondition playerCondition, nextPlayerCondition;
    private float nextX,nextY;

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

        textureAtlas =  new TextureAtlas(Gdx.files.internal(getPath() + "player/player.atlas"));;
        animation = MyMethods.createAnimation("player.png",36,1,1f);

        currentFrame.setRegion((TextureRegion) animation.getKeyFrame(0));

        String dialogJson = getJson("screens/dialogs/Ru.json");
        ArrayList<Message.MessageData> messageDataArrayList = json.fromJson(ArrayList.class, dialogJson);
        for (Message.MessageData messageData: messageDataArrayList) {
           try {
                messages.add(new Message(messageData));
            }catch (GdxRuntimeException ignored){}
        }

        String animJson = getJson("player/animation.json");
        aniimList = new TreeMap<>();
        ArrayList<PlayerAnimationData> animListData = json.fromJson(ArrayList.class, animJson);
        for (PlayerAnimationData animListData1: animListData) {
                aniimList.put(animListData1.name,getAnim(animListData1.name, animListData1.colls, animListData1.rows, animListData1.frameDur));
        }

        //TODO Make animation work with Texture atlas like it told here https://github.com/libgdx/libgdx/wiki/2D-Animation
        //It'll be much better
        animationTime = 0;
    }

    private Animation getAnim(String walking, int colls, int rows, float frameDuraction) {
        TextureRegion[][] tmp = textureAtlas.findRegion(walking).split((int) (textureAtlas.findRegion(walking).originalWidth / colls), (int) (textureAtlas.findRegion(walking).originalHeight/ rows));
        TextureRegion[] walkFrames = new TextureRegion[colls * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colls; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        return   new Animation<TextureRegion>(frameDuraction, walkFrames);
    }


    void setParameters() {
        setBounds(400,100,145,245);
        lastWalkeblePosition.set(getX(), getY());
        animationTime=0;
        fat_level=10;
        playerCondition=stay;
        setName("player");
        nextPlayerCondition=stay;
        nextX=0;
        nextY=0;
        body_weight=74;
        body_lean_mass=body_weight*(1 - fat_level / 100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentFrame,getX() - getWidth()/2, getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        playAnimation(delta);
        changePlayerCondition();
        Walk();
        calculateHours();
        showDialogs();
    }

    private void showDialogs() {
        for (Message message : messages) {
            if(playerCondition == talkToArmGirl && message.name.equals(talkToArmGirl.toString())){
                message.nextDialog();
                this.getStage().addActor(message);
                setPlayersAction(stay,0,0);
                break;
            } if(playerCondition == talkToBicepsGuy && message.name.equals(talkToBicepsGuy.toString())){
                message.nextDialog();
                this.getStage().addActor(message);
                setPlayersAction(stay,0,0);
                break;
            }
            if(playerCondition == talkToCoach && message.name.equals(talkToCoach.toString())){
                message.nextDialog();
                this.getStage().addActor(message);
                setPlayersAction(stay,0,0);
                break;
            }
        }
    }

    void setPlayerCondition(PlayerCondition playerCondition) {
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
                entry.getKey().setPosition(0,0 + entry.getKey().getHeight()*i);
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
        else if(body_lean_mass<=93) res=1.125f;
        else if(body_lean_mass<=105)res=1.1f;
        else if(body_lean_mass<=120)res=1;
        else res=1.05f;
        return res;
    }

    private void changePlayerCondition() {
        if(MyGdxGame.path != null)
            if(MyGdxGame.path.isEmpty()){
                playerCondition = nextPlayerCondition;
                if(nextX > 0 || nextY > 0){
                    setPosition(nextX , nextY);
                }
            }
            else if(speed.x > 0) playerCondition = right;
            else if(speed.x < 0) playerCondition = left;
            else if(speed.y > 0) playerCondition = up;
            else if(speed.y < 0) playerCondition = down;

    }

    private void playAnimation(float delta){
        animationTime += delta ;
        try {
            currentFrame.setRegion(aniimList.get(playerCondition.toString()).getKeyFrame(animationTime, true));
            setSize(currentFrame.getRegionWidth() * sizeMult, currentFrame.getRegionHeight() * sizeMult );
        }catch (NullPointerException ignored){}
    }

    void setPlayersAction(PlayerCondition playerCondition, int x, int y) {
        this.nextPlayerCondition = playerCondition;
        nextX = x;
        nextY = y;
        setStatVisible();
    }

    void setStatVisible() {
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
                    lastWalkeblePosition.set(getX(), getY());
                }
            }
    }


    PlayerCondition getPlayerCondition() {
        return this.playerCondition;

    }

    void ceilPos() {

        if(getX() % 10 != 5f )
           setX(getX() - getX() % 10 );
        if(getY() % 10 != 5 && getY() % 10 != 0)
            setY(getY() - getY() % 10 );
    }

    static class PlayerAnimationData{
        String name;
        int colls, rows;
        float frameDur;

        public PlayerAnimationData() {

        }
    }
}

