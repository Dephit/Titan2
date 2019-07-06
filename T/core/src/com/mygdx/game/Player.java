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
import static com.mygdx.game.Player.PlayerCondition.openRef;
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

    StatBar energy, food;
    StatBar health;
    ArrayList<StatBar> stats;

    private Vector2 speed;

    private float time;

    private float animationTime;
    private TextureRegion currentFrame = new TextureRegion();
    private Map<String, Animation<TextureRegion>> aniimList;
    Vector2 lastWalkeblePosition = new Vector2();
    private int sizeMult = 5;

    public enum PlayerCondition {
        //Walk
        stay, down, up, left, right,
        //Exercise
        squat, bench, deadlift, squatTechnic, pullUps,
        benchTechnic, deadliftTechnic, gripWorkout, archWorkout, legPress, pcSitting, hiper, pushups,
        //Other
        sleeping, working, sitting, sittingRev, openRef,
        //Dialog Triger
        talkToArmGirl, talkToBicepsGuy, talkToCoach
    }

    static class PlayerAnimationData{
        String name;
        int colls, rows;
        float frameDur;

        public PlayerAnimationData() {

        }
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

        StatBar workProgress = new StatBar("Работа", 150, 0, 300, 40);

        StatBar legStrenght = new StatBar("Сила ног", 310, 950, 79 * 5, 17 * 5);
        legStrenght.setLeveled(true,0,10);

        StatBar armStrenght = new StatBar("Сила рук", 620, 950, 79 * 5, 17 * 5);
        armStrenght.setLeveled(true,0,10);

        StatBar backStrenght = new StatBar("Сила спины", 930, 950, 79 * 5, 17 * 5);
        backStrenght.setLeveled(true,0,10);

        StatBar grip = new StatBar("Хват", 310, 900, 79 * 5, 17 * 5);
        grip.setLeveled(true,0,10);

        StatBar arch = new StatBar("Мост", 620, 900, 79 * 5, 17 * 5);
        arch.setLeveled(true,0,10);

        StatBar squatTechnique = new StatBar("Техника приседа", 930, 900, 79 * 5, 17 * 5);
        squatTechnique.setLeveled(true,0,10);

        StatBar benchTechnique = new StatBar("Техника жима", 310, 850, 79 * 5, 17 * 5);
        benchTechnique.setLeveled(true,0,10);

        StatBar deadliftTechnique = new StatBar("Техника тяги", 930, 850, 79 * 5, 17 * 5);
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


        setParameters();

        textureAtlas =  new TextureAtlas(Gdx.files.internal(getPath() + "player/player.atlas"));;
        Animation animation = MyMethods.createAnimation("player.png", 36, 1, 1f);

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
        float fat_level = 10;
        playerCondition=stay;
        setName("player");
        nextPlayerCondition=stay;
        nextX=0;
        nextY=0;
        float body_weight = 74;
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
            checkDialog(message, talkToArmGirl);
            checkDialog(message, talkToBicepsGuy);
            checkDialog(message, talkToCoach);
        }

    }

    private void checkDialog(Message message, PlayerCondition condition) {
        if(playerCondition == condition && message.name.equals(condition.toString())){
            message.nextDialog();
            this.getStage().addActor(message);
            setPlayersAction(stay,0,0);
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

     void changePlayerCondition() {
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
}

