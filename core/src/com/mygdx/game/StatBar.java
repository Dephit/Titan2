package com.mygdx.game;


import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class StatBar extends BaseActor {

    private float capacity = 100f;
    private float currentAmount;
    public float secondCapacity = 100f;
    public float secondCurrentAmount = 100;
    private Texture backgroundImg, progressImg, foregroundImg;
    private Sprite progressSprite, secondProgressSprite;
    private String str;

    public StatBar(String name) {
        setName(name);
        createTextures();
    }

    public float getCurrentAmount() {
        return currentAmount;
    }

    void setCapacity(int _capacity){
        capacity = _capacity;
    }

    public void setProgressAndCapacity(int _capacity, int _currentAmount){
        capacity = _capacity;
        currentAmount = _currentAmount;
    }

    void setProgress(float _progress){
        currentAmount = _progress;
    }

    void updateProgress(float _progress){
        currentAmount += _progress;
    }

    private void createTextures(){
        Preffics preffics = Preffics.getInstance();
        backgroundImg = preffics.loadImg("statBot.png");
        progressImg = preffics.loadImg("statMid.png");
        foregroundImg = preffics.loadImg("statUp.png");
        progressSprite = new Sprite(progressImg);
        secondProgressSprite = new Sprite(progressImg);
        secondProgressSprite.setColor(Color.GREEN);
        progressSprite.setColor(Color.CORAL);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        progressSprite.setColor(color);
    }

    public void setBackgroundColor(Color color){
        secondProgressSprite.setColor(color);
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
        batch.draw(backgroundImg, getX(), getY(), getWidth(), getHeight());
        secondProgressSprite.setBounds( getX() + 10, getY() + 5, getWidth() * secondCurrentAmount / secondCapacity - 10, getHeight() - 10);
        secondProgressSprite.draw(batch);
        progressSprite.setBounds( getX() + 10, getY() + 5, Math.min(getWidth() * currentAmount / capacity - 10, secondProgressSprite.getWidth()), getHeight() - 10);
        progressSprite.draw(batch);
        batch.draw(foregroundImg, getX(), getY(), getWidth(), getHeight());
        if(str != null)
            showText(batch, str, (int) (getX() + 25), (int) (getY() + getHeight() / 1.5f), 1f, Color.BLACK);
    }

    public void drawText(String str) {
        this.str = str;
    }

    public void addCurrentAmount(int i) {
        currentAmount += i;
    }
}


