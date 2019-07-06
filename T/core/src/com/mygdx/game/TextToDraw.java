package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.MyMethods.textDrawing;


class TextToDraw extends Actor {
    String str;
    float x, y, scale;
    Color color;

    public TextToDraw(String str, float x, float y, float scale, Color color) {
        this.str = str;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.color = color;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        textDrawing(batch, str, x, y, scale,color);
    }
}
