package com.mygdx.game;

/*
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
import static com.mygdx.game.Player.PlayerCondition.pushUps;
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

    private final TextureAtlas textureAtlas;

    private final ArrayList<Message> messages = new ArrayList<>();
    final float minBarWeight = 20;
    final float maxBarWeight = 500;
    private final Clock clock;
    float barWeight = 20;

    ArrayList<StatBar> stats;

    private Vector2 speed;

    float animationTime;
    private TextureRegion currentFrame = new TextureRegion();
    private Map<String, Animation<TextureRegion>> aniimList;
    Vector2 lastWalkablePosition = new Vector2();
    private int sizeMult = 5;
    boolean doExercise = true;

    float getHeight(PlayerCondition condition) {
        if(condition == PlayerCondition.squat )
            return 500 * (playerStats.squatRes / (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
        else if(condition == bench )
            return 500 * (playerStats.benchRes / (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
        else if(condition == deadlift )
            return 500 * (playerStats.deadliftRes / (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
        else if(condition == legPress)
            return 500 * ((playerStats.legStrenght.getLevel() + 1) / (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
        else if(condition == pullUps)
            return 500 * ((playerStats.backStrenght.getLevel() + 1)/ (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
        else if(condition == pushUps)
            return 500 * ((playerStats.armStrenght.getLevel() + 1) / (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
        else if(condition == hiper)
            return 500 * ((playerStats.backStrenght.getLevel() + 1) / (barWeight * 4)) * (playerStats.stress.getCurrentAmount());
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
            case pushUps: return playerStats.armStrenght.getLevel();
            case sleeping: return barWeight / 0.6f;
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
            return (int) (8 * (barWeight * 4) / (playerStats.legStrenght.getLevel() + 1) * (1.5f - playerStats.stress.getCurrentAmount()));
        else   if(playerCondition == pullUps)
            return (int) (8 * (barWeight * 4) / (playerStats.backStrenght.getLevel() + 1) * (1.5f - playerStats.stress.getCurrentAmount()));
        else   if(playerCondition == pushUps)
            return (int) (8 * (barWeight * 4) / (playerStats.armStrenght.getLevel() + 1) * (1.5f - playerStats.stress.getCurrentAmount()));
        else   if(playerCondition == hiper)
            return (int) (8 * (barWeight * 4) / (playerStats.backStrenght.getLevel() + 1) * (1.5f - playerStats.stress.getCurrentAmount()));
        return 0;
    }

    public float getResPercent(float v, float squatRes) {
        float res = squatRes * v;
        if(res % 10 >= 7.5){
            res -= res % 10 + 7.5f;
        }else if(res % 10 >= 5){
            res -= res % 10 + 5f;
        }else if(res % 10 >= 2.5){
            res -= res % 10 + 2.5f;
        }else if(res % 10 >= 0){
            res -= res % 10;
        }
        return res;
    }

    public enum PlayerCondition {
        //Walk
        stay, down, up, left, right,
        //Exercise
        squat, bench, deadlift, squatTechnic, pullUps,
        benchTechnic, deadliftTechnic, gripWorkout, archWorkout, legPress, pcSitting, hiper, pushUps,
        //Competition
       compSquat, compBench, compDeadlift,
        //Other
        sleeping, working, sitting, sittingRev, openRef, watchShop, watchLoli, watchCam, lookinLeft, lookinRight, lookinUp, goBuying,
        //Dialog Triger
        talkToArmGirl, talkToBicepsGuy, talkToCoach, talkToStaff, talkToCleaner,
        //goTo
        goToSquatRack, goToBenchRack, goToDeadliftRack, goToLegPress, goToPullUps, goToPushUps, goToHiper
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

        clock = new Clock();
        stats = clock.getClock();

        setParameters();
        textureAtlas =  new TextureAtlas(Gdx.files.internal(getPath() + "player/player.atlas"));;

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

    private Animation getAnim(String name, int colls, int rows, float frameDuraction) {
        TextureRegion[][] tmp = textureAtlas.findRegion(name).split((textureAtlas.findRegion(name).originalWidth / colls), (textureAtlas.findRegion(name).originalHeight/ rows));
        TextureRegion[] animation = new TextureRegion[colls * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colls; j++) {
                animation[index++] = tmp[i][j];
            }
        }
        return new Animation(frameDuraction, animation);
    }

    void setParameters() {
        setBounds(400,100,145,245);
        lastWalkablePosition.set(getX(), getY());
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
        clock.calculateHours();
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

    void     setPlayerCondition(PlayerCondition playerCondition) {
        this.playerCondition = playerCondition;
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
        float variable = barWeight / getProgerss(playerCondition);
        float positiveValue =  variable <= 0.7f ? 0.1f :
                               variable <= 0.8f ? 0.2f :
                               variable <= 0.9f ? 0.4f :
                                       0.5f ;
        float negativeValue =  variable <= 0.7f ? 0.05f :
                               variable <= 0.8f ? 0.1f :
                               variable <= 0.9f ? 0.15f :
                                       0.2f ;
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
                    lastWalkablePosition.set(getX(), getY());
                }
            }
    }

    PlayerCondition getPlayerCondition() {
        return this.playerCondition;

    }

    void ceilPos() {
           setPosition(getX() % 10 != 5f ? getX() - getX() % 10 : getX(), getY() % 10 != 5 && getY() % 10 != 0 ? getY() - getY() % 10 : getY());
    }
}

*/
