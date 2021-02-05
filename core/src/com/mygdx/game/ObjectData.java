package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class ObjectData extends Actor {
    public String name;
    int x, y, width, height, rows, cols, time;
    private boolean isCertainFrame = false;

    List<int[]> animations = new ArrayList<int[]>();

    private Animation animation;
    private float animationTime;
    private TextureRegion currentFrame;
    private int randomTime = 0;

    public ObjectData() {
    }

    public void setAtlas(TextureAtlas textureAtlas) {
        animationTime = 0;

        try{
            randomTime = randomAnim();
        }catch (IllegalArgumentException e){
            animations.add(new int[]{0, rows * cols, time, 100});
            randomTime = randomAnim();
        }

        TextureRegion[][] tmp = textureAtlas.findRegion(name).split(
                (textureAtlas.findRegion(name).originalWidth / cols),
                (textureAtlas.findRegion(name).originalHeight/ rows));

        TextureRegion[] walkFrames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<>(1f, walkFrames);
        currentFrame = (TextureRegion) animation.getKeyFrame(animationTime,true);
        setBounds((int)x, (int)y, currentFrame.getRegionWidth() * 5, currentFrame.getRegionHeight() * 5);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        doRandomAnim(delta);
    }

    private int randomAnim(){
        for (int i = 0; i < animations.size(); i++) {
            if(100 - animations.get(i)[3] <= MathUtils.random(0,100)){
                if(animations.get(i)[3] != 100 )
                    return i;
            }
        }
        return MathUtils.random(0, animations.size() - 1);
    }

    private void doRandomAnim(float delta) {
        if (!isCertainFrame) {
            animationTime += delta * animations.get(randomTime)[2];
            if (!(animationTime < animations.get(randomTime)[1]) || !(animationTime > animations.get(randomTime)[0])) {
                randomTime = randomAnim();
                animationTime = animations.get(randomTime)[0];
            }
        } else {
            animationTime = animation.getAnimationDuration() - animation.getFrameDuration() / 2;
        }
        currentFrame.setRegion((TextureRegion) animation.getKeyFrame(animationTime, true));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentFrame, getX(), getY() , getWidth() ,getHeight() );
    }

    public void dispose()
    {
        if(currentFrame.getTexture()!= null)
            currentFrame.getTexture().dispose();
    }


    void setCertainFrame(boolean certainFrame) {
        isCertainFrame = certainFrame;
    }
}
