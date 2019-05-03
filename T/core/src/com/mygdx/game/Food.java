package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Food extends Actor{

    private Animation animation;
    private TextureRegion currentFrame;
    public float add_energy, add_food, add_health;

    public Food(float x, float y, float width, float height) {
        setBounds(x,y,width,height);
        setName("food");
        animation=MyMethods.createAnimation("Potato.png",1,1,1f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        currentFrame = (TextureRegion) animation.getKeyFrame(1, true);
        batch.draw(currentFrame,getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
