package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

class TextView extends Actor {
    String str;
    float x, y, scale;
    Color color;

    public void setText(String _str){
        str = _str;
    }

    public TextView() {
        this.str = "";
        this.x = 0;
        this.y = 0;
        this.scale = 1;
        this.color = Color.WHITE;
    }

    public TextView(String str, float x, float y, float scale, Color color) {
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

    public void textDrawing(Batch batch, String str, float x, float y, float scale, Color color) {
        BitmapFont textFont = FontFactory.getInstance().getFont(Preffics.getInstance().getLocale());
        textFont.getData().setScale(scale);
        textFont.setColor(color);
        textFont.draw(batch, str, x, y);
        textFont.getData().setScale(1);
        //textFont.setColor(Color.BLACK);
    }
}
