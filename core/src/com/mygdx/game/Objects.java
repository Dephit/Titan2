package com.mygdx.game;

/*

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.MyGdxGame.Screens.gym;
import static com.mygdx.game.MyGdxGame.mapArr;
import static com.mygdx.game.MyGdxGame.mapCoorinateCorrector;
import static com.mygdx.game.MyMethods.*;

public class Objects extends Actor
{
    private Animation animation;
    private float animationTime;
    private TextureRegion currentFrame;
    private List<int[]> animations=new ArrayList<int[]>();
    private int randomTime = 0;
    private boolean isCertainFrame = false;
    private Vector2 speed = new Vector2();
    private List<Grid2d.MapNode> path;
    private RocketCond rocketAnim = RocketCond.layDown;
    enum RocketCond{
        up, down, left, right, layDown, standUp
    }

    Objects(String path, MyGdxGame.ObjectData objectData, TextureAtlas textureAtlas) {
        setName(objectData.name);
        int rows = objectData.rows;
        int colls = objectData.cols;
        int time = objectData.time;
        animationTime=0;

        try{
            animations = objectData.animations;
            randomTime = randomAnim();
        }catch (IllegalArgumentException e){
            animations.add(new int[]{0, rows * colls, time, 100});
            randomTime = randomAnim();
        }

        TextureRegion[][] tmp = textureAtlas.findRegion(getName()).split(
                (textureAtlas.findRegion(getName()).originalWidth / colls),
                (textureAtlas.findRegion(getName()).originalHeight/ rows));

        TextureRegion[] walkFrames = new TextureRegion[colls * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colls; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(1f, walkFrames);
        currentFrame = (TextureRegion) animation.getKeyFrame(animationTime,true);
        setBounds((int)objectData.x, (int)objectData.y, currentFrame.getRegionWidth() * 5, currentFrame.getRegionHeight() * 5);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentFrame, getX(), getY() , getWidth() ,getHeight() );
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
                if (animationTime < animations.get(randomTime)[1] && animationTime > animations.get(randomTime)[0]) { }
                else {
                        randomTime = randomAnim();
                    animationTime = animations.get(randomTime)[0];
                }
            } else {
                animationTime = animation.getAnimationDuration() - animation.getFrameDuration() / 2;
            }
            currentFrame.setRegion((TextureRegion) animation.getKeyFrame(animationTime, true));
     }

    public void dispose()
    {
        if( currentFrame.getTexture()!= null)
        currentFrame.getTexture().dispose();
    }

    void setCertainFrame(boolean certainFrame) {
        isCertainFrame = certainFrame;
    }

}
*/
