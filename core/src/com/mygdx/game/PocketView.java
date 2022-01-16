package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PocketView extends BaseActor {

    private String str;

    public PocketView(String name) {
        setName(name);
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        /*if(currentAmount > capacity)
            currentAmount = 0;
        if(currentAmount > secondCurrentAmount)
            currentAmount = 0;*/

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (str != null)
            showText(batch, str, (int) (getX() + 25), (int) (getY() + getHeight() / 1.5f), 1f, Color.BLACK);
    }

    public void drawText(String str) {
        this.str = str;
    }
}
