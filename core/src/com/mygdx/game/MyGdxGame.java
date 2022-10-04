package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.interfaces.OnClickCallback;
import com.mygdx.game.interfaces.OnClickBooleanCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.enums.Comp;
import com.mygdx.game.rooms.CompetitionRoom;
import com.mygdx.game.rooms.GymRoom;
import com.mygdx.game.rooms.MapRoom;
import com.mygdx.game.rooms.ParkRoom;
import com.mygdx.game.rooms.RoomRoom;
import com.mygdx.game.rooms.ShopRoom;
import com.mygdx.game.rooms.WorkRoom;

import java.util.List;

public class MyGdxGame implements ApplicationListener, InterScreenCommunication {

    BaseRoom stage;
    Player player;

    private final IActivityRequestHandler myRequestHandler;

    public MyGdxGame(IActivityRequestHandler handler) {
        myRequestHandler = handler;
    }

    @Override
    public void create() {
        Preffics.getInstance();
        player = new Player(Preffics.getInstance().getLanguage());
        openGym();
    }

    @Override
    public void resize(int width, int height) {
        stage.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.onRender();
        if(player != null){
            showHud();
        }
    }

    @Override
    public void showHud() {
        myRequestHandler.showHud(player);
    }

    @Override
    public void openStats(Runnable pauseGame) {
        myRequestHandler.openStats(player, pauseGame);
    }

    @Override
    public void openPerkMenu(Runnable pauseGame) {
        myRequestHandler.openPerkMenu(player, pauseGame);
    }

    @Override
    public void pause() {
        stage.onPause();
    }

    @Override
    public void resume() {
        stage.onRender();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void showToast(String msg) {
        myRequestHandler.showToast(msg);
    }

    @Override
    public void showPlayerList(List<CompetitionOpponent> playerList, Comp status, OnClickCallback runnable) {
        myRequestHandler.showPlayers(playerList, status, runnable);
    }

    @Override
    public void showNextSetMenu(Player player, int currentSet, List<CompetitionOpponent> playerList, OnClickCallback onFirstClick, OnClickCallback onClose) {
        myRequestHandler.showNextSetMenu(player, currentSet, playerList, onFirstClick, onClose);
    }

    @Override
    public void showDialog(String title, String subtitle, OnClickCallback onClose, OnClickCallback onAgree) {
        myRequestHandler.showDialog(title, subtitle, onClose, onAgree);
    }

    @Override
    public void showProgressBar(String title, OnClickCallback onProgressEnd, OnClickBooleanCallback onProgressUpdate, OnClickCallback onClose) {
        myRequestHandler.showProgressBar(title, onProgressEnd, onProgressUpdate, onClose);
    }

    @Override
    public void showDialog(String title, String subtitle, String agreeText, String closeText, OnClickCallback onClose, OnClickCallback onAgree) {
        myRequestHandler.showDialog(title, subtitle, agreeText, closeText, onClose, onAgree);
    }


    @Override
    public void openMap() {
        stage = new MapRoom(this, player);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.onTouchDown();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void openGym() {
        stage = new GymRoom(this, player);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.onTouchDown();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void openRoom() {
        stage = new RoomRoom(this, player);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.onTouchDown();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void openShop() {
        stage = new ShopRoom(this, player);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.onTouchDown();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    @Override
    public void openWork() {
        stage = new WorkRoom(this, player);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.onTouchDown();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    @Override
    public void openPark() {
        stage = new ParkRoom(this, player);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.onTouchDown();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void openCompetition() {
        stage = new CompetitionRoom(this, player);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //stage.onTouchDown();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void openOptions() {
        myRequestHandler.openOptions();
  //      myRequestHandler.openInventory(player);
    }

    @Override
    public void openRefrigerator(Player player, Runnable runnable) {
        myRequestHandler.openRefrigerator(player, runnable);
    }

    @Override
    public void showBuyMenu(Container container, OnClickCallback onBuyRunnable, Runnable runnable) {
        myRequestHandler.openShopByMenu(container, onBuyRunnable, runnable);
    }


    @Override
    public void openInventory(Runnable runnable) {
        myRequestHandler.openInventory(player, runnable);
    }

}

