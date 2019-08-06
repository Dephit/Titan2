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

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import static com.mygdx.game.MyGdxGame.playerStats;
import static com.mygdx.game.MyMethods.getJson;
import static com.mygdx.game.MyMethods.getPath;
import static com.mygdx.game.Player.PlayerCondition.bench;
import static com.mygdx.game.Player.PlayerCondition.compBench;
import static com.mygdx.game.Player.PlayerCondition.compDeadlift;
import static com.mygdx.game.Player.PlayerCondition.compSquat;
import static com.mygdx.game.Player.PlayerCondition.deadlift;
import static com.mygdx.game.Player.PlayerCondition.down;
import static com.mygdx.game.Player.PlayerCondition.hiper;
import static com.mygdx.game.Player.PlayerCondition.left;
import static com.mygdx.game.Player.PlayerCondition.legPress;
import static com.mygdx.game.Player.PlayerCondition.lookinLeft;
import static com.mygdx.game.Player.PlayerCondition.lookinUp;
import static com.mygdx.game.Player.PlayerCondition.pullUps;
import static com.mygdx.game.Player.PlayerCondition.pushups;
import static com.mygdx.game.Player.PlayerCondition.right;
import static com.mygdx.game.Player.PlayerCondition.squat;
import static com.mygdx.game.Player.PlayerCondition.stay;
import static com.mygdx.game.Player.PlayerCondition.talkToCleaner;
import static com.mygdx.game.Player.PlayerCondition.talkToCoach;
import static com.mygdx.game.Player.PlayerCondition.talkToArmGirl;
import static com.mygdx.game.Player.PlayerCondition.talkToBicepsGuy;
import static com.mygdx.game.Player.PlayerCondition.talkToStaff;
import static com.mygdx.game.Player.PlayerCondition.up;

public class Player extends Actor {
    private final StatBar days;
    private final StatBar minutes;
    private final StatBar hours;
    private final StatBar hoursSecond;
    private final StatBar minutesSecond;

    private final TextureAtlas textureAtlas;

    private final ArrayList<Message> messages = new ArrayList<>();
    final float minBarWeight = 20;
    final float maxBarWeight = 500;
    float barWeight = 20;

    ArrayList<StatBar> stats;

    private Vector2 speed;

    private float time;

    float animationTime;
    private TextureRegion currentFrame = new TextureRegion();
    private Map<String, Animation<TextureRegion>> aniimList;
    Vector2 lastWalkeblePosition = new Vector2();
    private int sizeMult = 5;
    boolean doExercise = true;

    float getHeight(PlayerCondition condition) {
        if(condition == PlayerCondition.squat)
            return 500 * (playerStats.squatRes / (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
        else if(condition == bench)
            return 500 * (playerStats.benchRes / (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
        else if(condition == deadlift)
            return 500 * (playerStats.deadliftRes / (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
        else if(condition == legPress)
            return 500 * (playerStats.legStrenght.getLevel() / (barWeight)) * (playerStats.stress.getCurrentAmount());
        else if(condition == pullUps)
            return 500 * (playerStats.backStrenght.getLevel() / (barWeight)) * (playerStats.stress.getCurrentAmount());
        else if(condition == pushups)
            return 500 * (playerStats.archStrenght.getLevel() / (barWeight)) * (playerStats.stress.getCurrentAmount());
        else if(condition == hiper)
            return 500 * (playerStats.backStrenght.getLevel() / (barWeight)) * (playerStats.stress.getCurrentAmount());
        return 0;
    }

    float getProgerss(PlayerCondition condition){
        switch (condition){
            case bench: return playerStats.benchRes;
            case squat: return playerStats.squatRes;
            case deadlift: return playerStats.deadliftRes;
            case hiper: return playerStats.backStrenght.getLevel();
            case legPress: return playerStats.legStrenght.getLevel();
            case pullUps: return playerStats.backStrenght.getLevel();
            case pushups: return playerStats.armStrenght.getLevel();
        }
        return 0;
    }

    int getSpeed() {
        if(playerCondition == squat)
            return (int) (8 * (barWeight * 4) / playerStats.squatRes * (1.5f - playerStats.stress.getCurrentAmount()));
        else   if(playerCondition == bench)
            return (int) (8 * (barWeight * 4) / playerStats.benchRes * (1.5f - playerStats.stress.getCurrentAmount()));
        else   if(playerCondition == deadlift)
            return (int) (8 * (barWeight * 4) / playerStats.deadliftRes * (1.5f - playerStats.stress.getCurrentAmount()));
        else   if(playerCondition == legPress)
            return (int) (8 * (barWeight * 4) / playerStats.legStrenght.getLevel() * (1.5f - playerStats.stress.getCurrentAmount()));
        else   if(playerCondition == pullUps)
            return (int) (8 * (barWeight * 4) / playerStats.backStrenght.getLevel() * (1.5f - playerStats.stress.getCurrentAmount()));
        else   if(playerCondition == pushups)
            return (int) (8 * (barWeight * 4) / playerStats.armStrenght.getLevel() * (1.5f - playerStats.stress.getCurrentAmount()));
        else   if(playerCondition == hiper)
            return (int) (8 * (barWeight * 4) / playerStats.backStrenght.getLevel() * (1.5f - playerStats.stress.getCurrentAmount()));
        return 0;
    }

    public enum PlayerCondition {
        //Walk
        stay, down, up, left, right,
        //Exercise
        squat, bench, deadlift, squatTechnic, pullUps,
        benchTechnic, deadliftTechnic, gripWorkout, archWorkout, legPress, pcSitting, hiper, pushups,
        //Competition
       compSquat, compBench, compDeadlift,
        //Other
        sleeping, working, sitting, sittingRev, openRef, watchShop, watchLoli, watchCam, lookinLeft, lookinRight, lookinUp, goBuying,
        //Dialog Triger
        talkToArmGirl, talkToBicepsGuy, talkToCoach, talkToStaff, talkToCleaner,
        //goTo
        goToSquatRack, goToBenchRack, goToDeadliftRack
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
        speed = new Vector2();

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

        stats = new ArrayList();
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
        playerCondition = stay;
        setName("player");
        nextPlayerCondition=stay;
        nextX = 0;
        nextY = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentFrame,getX() - getWidth()/2, getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isAnimationFinished()) {
            animationTime = 0;
            calculateProgress();
            playerStats.calcRes();
        }
        if(doExercise)
            animationTime += delta ;
        if(playerCondition == compSquat || playerCondition == compBench || playerCondition == compDeadlift)
            playCompAnimation(delta);
        else
            playAnimation(delta);
        changePlayerCondition();
        walk();
        calculateHours();
        showDialogs();
    }

    private void showDialogs() {
        for (Message message : messages) {
            checkDialog(message, talkToArmGirl, lookinUp);
            checkDialog(message, talkToBicepsGuy, lookinLeft);
            checkDialog(message, talkToCoach, lookinLeft);
            checkDialog(message, talkToCleaner, lookinLeft);
            checkDialog(message, talkToStaff, lookinLeft);
        }
    }

    private void checkDialog(Message message, PlayerCondition condition, PlayerCondition nextPlayerCondition) {
        if(playerCondition == condition && message.name.equals(condition.toString())){
            message.nextDialog();
            this.getStage().addActor(message);
            setPlayersAction(nextPlayerCondition,0,0);
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
        try {
            currentFrame.setRegion(aniimList.get(playerCondition.toString()).getKeyFrame(animationTime, true));
            setSize(currentFrame.getRegionWidth() * sizeMult, currentFrame.getRegionHeight() * sizeMult);
        }catch (NullPointerException ignored){}
    }

    boolean isAnimationFinished(){
        return aniimList.get(playerCondition.toString()) != null && aniimList.get(playerCondition.toString()).getAnimationDuration() < animationTime;
    }
    boolean isAnimationStarted(){
        return animationTime > 0;
    }

    private void calculateProgress() {
        float positiveValue =   barWeight / getProgerss(playerCondition) <= 0.7f ? 0.1f * playerStats.energy.getCurrentAmount() :
                                barWeight / getProgerss(playerCondition) <= 0.8f ? 0.2f * playerStats.energy.getCurrentAmount() :
                                barWeight / getProgerss(playerCondition) <= 0.9f ? 0.4f * playerStats.energy.getCurrentAmount() :
                                                                           0.5f * playerStats.energy.getCurrentAmount();
        float negativeValue =   barWeight / getProgerss(playerCondition) <= 0.7f ? 0.01f * playerStats.energy.getCurrentAmount() :
                                barWeight / getProgerss(playerCondition) <= 0.8f ? 0.1f * playerStats.energy.getCurrentAmount() :
                                barWeight / getProgerss(playerCondition) <= 0.9f ? 0.15f * playerStats.energy.getCurrentAmount() :
                                                                            0.2f * playerStats.energy.getCurrentAmount();
        switch (playerCondition){
            case working:{
                playerStats.money ++;
                playerStats.energy.addProgress(-negativeValue);
                playerStats.stress.addProgress( 0.5f * -negativeValue );

                playerStats.deadliftTechnic.addProgress( -negativeValue);
                playerStats.squatTechnic.addProgress( -negativeValue);
                playerStats.benchTechnic.addProgress( -negativeValue);
                playerStats.backStrenght.addProgress( -negativeValue);
                playerStats.legStrenght.addProgress(-negativeValue);
                playerStats.gripStrenght.addProgress( -negativeValue);
                playerStats.archStrenght.addProgress( -negativeValue);
                playerStats.armStrenght.addProgress( -negativeValue);
                playerStats.food.addProgress(-0.5f * negativeValue);

                break;}
            case squat:{
                doExercise = false;

                playerStats.legStrenght.addProgress(0.25f * positiveValue);
                playerStats.backStrenght.addProgress( 0.25f * positiveValue);
                playerStats.squatTechnic.addProgress(positiveValue);

                playerStats.energy.addProgress(- negativeValue * playerStats.food.getCurrentAmount());
                playerStats.food.addProgress(- negativeValue);
                playerStats.stress.addProgress(-1.5f * negativeValue);
                break;}
            case bench:{
                doExercise = false;
                playerStats.armStrenght.addProgress( 0.5f * positiveValue);
                playerStats.archStrenght.addProgress( 0.25f * positiveValue);
                playerStats.benchTechnic.addProgress(positiveValue);

                playerStats.energy.addProgress(-negativeValue);
                playerStats.food.addProgress(-negativeValue);
                playerStats.stress.addProgress(-2 * negativeValue);
                break;}
            case deadlift:{
                doExercise = false;
                playerStats.deadliftTechnic.addProgress(positiveValue);
                playerStats.backStrenght.addProgress(0.5f * positiveValue);
                playerStats.gripStrenght.addProgress(0.5f * positiveValue);

                playerStats.energy.addProgress(-negativeValue);
                playerStats.food.addProgress(-negativeValue);
                playerStats.stress.addProgress(-2 * negativeValue);
                break;}
            case hiper:{
                doExercise = false;
                playerStats.backStrenght.addProgress(positiveValue);
                playerStats.energy.addProgress(0.5f * -negativeValue);
                playerStats.stress.addProgress(0.25f * -negativeValue);
                playerStats.food.addProgress(-negativeValue);

                playerStats.deadliftTechnic.addProgress(0.5f * -negativeValue);
                playerStats.squatTechnic.addProgress(0.5f * -negativeValue);
                playerStats.benchTechnic.addProgress(0.5f * -negativeValue);
                break;}
            case pullUps:{
                doExercise = false;
                playerStats.backStrenght.addProgress(positiveValue);
                playerStats.energy.addProgress(0.5f * -negativeValue);
                playerStats.stress.addProgress(0.25f * -negativeValue);
                playerStats.food.addProgress(-negativeValue);

                playerStats.deadliftTechnic.addProgress(0.5f * -negativeValue);
                playerStats.squatTechnic.addProgress(0.5f * -negativeValue);
                playerStats.benchTechnic.addProgress(0.5f * -negativeValue);
                break;}
            case sleeping:{
                playerStats.energy.addProgress(2 * negativeValue );
                playerStats.stress.addProgress( negativeValue );

                playerStats.deadliftTechnic.addProgress( -negativeValue);
                playerStats.squatTechnic.addProgress( -negativeValue);
                playerStats.benchTechnic.addProgress( -negativeValue);
                playerStats.backStrenght.addProgress( -negativeValue);
                playerStats.legStrenght.addProgress(-negativeValue);
                playerStats.gripStrenght.addProgress( -negativeValue);
                playerStats.archStrenght.addProgress( -negativeValue);
                playerStats.armStrenght.addProgress( -negativeValue);
                playerStats.food.addProgress(-0.5f * negativeValue);
                break;}
            case pushups: {
                doExercise = false;
                playerStats.armStrenght.addProgress(positiveValue);

                playerStats.energy.addProgress(0.5f * -negativeValue);
                playerStats.stress.addProgress(0.25f * -negativeValue);
                playerStats.food.addProgress(-negativeValue);

                playerStats.deadliftTechnic.addProgress(0.2f * -negativeValue);
                playerStats.squatTechnic.addProgress(0.2f * -negativeValue);
                playerStats.benchTechnic.addProgress(0.2f * -negativeValue);
                break;}
            case legPress:{
                doExercise = false;
                playerStats.legStrenght.addProgress(positiveValue);

                playerStats.energy.addProgress(0.5f * -negativeValue);
                playerStats.stress.addProgress(0.25f * -negativeValue);
                playerStats.food.addProgress(-negativeValue);

                playerStats.deadliftTechnic.addProgress(0.5f * -negativeValue);
                playerStats.squatTechnic.addProgress(0.5f * -negativeValue);
                playerStats.benchTechnic.addProgress(0.5f * -negativeValue);
                break;}
            case sitting:{
                playerStats.energy.addProgress(0.2f * negativeValue);
                playerStats.stress.addProgress(0.1f * negativeValue);

                break;}
            case pcSitting:{
                playerStats.energy.addProgress(0.5f * -negativeValue);
                playerStats.stress.addProgress(2 * negativeValue);
                playerStats.food.addProgress(0.25f * -negativeValue);

                playerStats.archStrenght.addProgress( -negativeValue);
                playerStats.armStrenght.addProgress( -negativeValue);
                playerStats.legStrenght.addProgress( -negativeValue);
                playerStats.backStrenght.addProgress( -negativeValue);
                playerStats.deadliftTechnic.addProgress( -negativeValue);
                playerStats.squatTechnic.addProgress( -negativeValue);
                playerStats.benchTechnic.addProgress( -negativeValue);
                break;}
            case archWorkout: {
                playerStats.energy.addProgress(0.25f * -negativeValue);
                playerStats.stress.addProgress(0.1f * -negativeValue);
                playerStats.food.addProgress(-negativeValue);
                playerStats.archStrenght.addProgress(positiveValue);
                break;}
            case gripWorkout:{
                playerStats.energy.addProgress(0.25f * -negativeValue);
                playerStats.stress.addProgress(0.1f * -negativeValue);
                playerStats.food.addProgress(-negativeValue);
                playerStats.gripStrenght.addProgress(positiveValue);
                break;}
            case sittingRev:{
                playerStats.energy.addProgress(0.2f * negativeValue);
                playerStats.stress.addProgress(0.1f * negativeValue);

                break;}
        }
    }

    private void playCompAnimation(float delta){
        if(isAnimationFinished())
            animationTime += delta ;
        try {
            currentFrame.setRegion(aniimList.get(playerCondition.toString()).getKeyFrame(animationTime, true));
            setSize(currentFrame.getRegionWidth() * sizeMult, currentFrame.getRegionHeight() * sizeMult);
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

    private void walk() {
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

