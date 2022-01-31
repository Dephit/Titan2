package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.rooms.CompetitionRoom;
import com.mygdx.game.rooms.GymRoom;
import com.mygdx.game.rooms.MapRoom;
import com.mygdx.game.rooms.RoomRoom;
import com.mygdx.game.rooms.ShopRoom;
import com.mygdx.game.rooms.WorkRoom;

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
        player = new Player();
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
    }

    @Override
    public void pause() {
        stage.onPause();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void showToast(String msg) {
        myRequestHandler.showToast(msg);
    }

    @Override
    public void openMap() {
        stage = new MapRoom(this, null);
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
}

