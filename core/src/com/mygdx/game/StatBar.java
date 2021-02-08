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
    private Texture backgroundImg, progressImg, foregroundImg;
    private Sprite progressSprite;

    StatBar(String name) {
        setName(name);
        createTextures();
    }

    void setCapacity(int _capacity){
        capacity = _capacity;
    }

    void setProgressAndCapacity(int _capacity, int _currentAmount){
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
        setColor(Color.CORAL);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        progressSprite.setColor(color);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(currentAmount > capacity)
            currentAmount = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(backgroundImg, getX(), getY(), getWidth(), getHeight());
        //batch.draw(progressImg, getX(), getY(), getWidth() * currentAmount / capacity, getHeight());
        progressSprite.setBounds( getX() + 10, getY() + 5, getWidth() * currentAmount / capacity - 10, getHeight() - 10);
        progressSprite.draw(batch);
        batch.draw(foregroundImg, getX(), getY(), getWidth(), getHeight());
    }
}

