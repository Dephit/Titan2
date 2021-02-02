package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyGdxGame implements ApplicationListener {

    BaseRoom stage;

    private final IActivityRequestHandler myRequestHandler;

    public MyGdxGame(IActivityRequestHandler handler) {
        myRequestHandler = handler;
    }

    @Override
    public void create() {
        Preffics.getInstance();
        stage = new BaseRoom(new FitViewport(Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT));
        stage = new GymRoom();
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.onTouchDown(event, x, y, pointer, button);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
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

}


