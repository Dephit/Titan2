package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BaseRoom extends Stage {

    protected String ROOM_TAG = "";
    protected String BACKGROUND_PATH_TAG;

    private Texture background;

    private OrthographicCamera camera;
    private Vector3 touchPos;

    public BaseRoom(String tag) {
        super(new FitViewport(Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT));
        ROOM_TAG = tag;
        BACKGROUND_PATH_TAG = "screens/" + ROOM_TAG + "/" + ROOM_TAG + ".png";

        init();
    }

    public void init() {
        Preffics.getInstance().load(BACKGROUND_PATH_TAG, Texture.class);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT);
        touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Gdx.input.setInputProcessor(this);
    }

    public BaseRoom(FitViewport viewport) {
        super(viewport);
        init();
    }

    public void onTouchDown(InputEvent event, float x, float y, int pointer, int button) {
        touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
    }

    public void resize(int width, int height) {
        getViewport().update(width,height);
        camera.update();
    }

    public void onRender() {
        Preffics preffics = Preffics.getInstance();
        camera.update();
        getBatch().setProjectionMatrix(camera.combined);
        getBatch().begin();
        if(preffics.isLoaded()) {
            onLoaded();
        } else {
            preffics.updateLoading();
            showText( preffics.getLanguage().loading + ": " + preffics.getLoadingProgress(), 500, 500, 2, Color.CORAL);
        }
        getBatch().end();
        draw();
        act(Gdx.graphics.getDeltaTime());
    }

    private void showText(String str, int x, int y, int scale, Color color) {
        BitmapFont textFont = FontFactory.getInstance().getFont(Preffics.getInstance().getLocale());
        textFont.getData().setScale(scale);
        textFont.setColor(color);
        textFont.draw(getBatch(), str, x, y);
        textFont.getData().setScale(1);
    }

    private void onLoaded() {
        if(background == null)
            background = Preffics.getInstance().getByPath(BACKGROUND_PATH_TAG, Texture.class);
        else
            getBatch().draw(background, 0, 0, getWidth(), getHeight());
    }

    public void onPause() {
    }

    @Override
    public void clear() {
        super.clear();
        if (background != null)
            background.dispose();
    }
}

