package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MyInputListener extends InputListener {

    public String text;

    public MyInputListener(String text) {
        this.text = text;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        return super.touchDown(event, x, y, pointer, button);
    }

    public InputListener onTouchDown(Runnable runnable) {
        runnable.run();
        return this;
    }

}
