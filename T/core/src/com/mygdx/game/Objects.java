package com.mygdx.game;


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
   // private int x;
   // private int y;
  //  private int width;
  //  private int height;
    private TextureRegion currentFrame;
    private List<int[]> animations=new ArrayList<int[]>();
    private int randomTime = 0;
    private boolean isCertainFrame = false;
    private Vector2 speed = new Vector2();
    private List<Grid2d.MapNode> path;
    RocketCond rocketAnim = RocketCond.layDown;
    enum RocketCond{
        up, down, left, right, layDown, standUp
    }

    Objects(String path, MyGdxGame.ObjectData objectData, TextureAtlas textureAtlas) {
    //    String imgName = getDirectoryFiles(path, objectData.name)[0];
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

        if(getName().equals("rocket")){
         //   changeRocket();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame, getX(), getY() , getWidth() ,getHeight() );

if(Gdx.input.justTouched())
        if(getName().equals("rocket")){


        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getName().equals("rocket")){
           // changeRocket();
       //     Walk();
        }
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

    /*    if (getName().equals("rocket")) {
            if (speed.x > 0) {
                rocketAnim = RocketCond.right;
            } else if (speed.x < 0) {
                rocketAnim = RocketCond.left;
            } else if (speed.y < 0) {
                rocketAnim = RocketCond.down;
            } else if (speed.y > 0) {
                rocketAnim = RocketCond.up;
            }
            rocketAnim();
        }*/
            if (!isCertainFrame) {
                animationTime += delta * animations.get(randomTime)[2];
                if (animationTime < animations.get(randomTime)[1] && animationTime > animations.get(randomTime)[0]) {
                } else {
                   // if(!getName().equals("rocket"))
                        randomTime = randomAnim();
                  //  else
                     //   if(rocketAnim == RocketCond.layDown || rocketAnim == RocketCond.standUp)
                          //  changeRocket();
                    animationTime = animations.get(randomTime)[0];

                }
            } else {
                animationTime = animation.getAnimationDuration() - animation.getFrameDuration() / 2;
            }
            currentFrame.setRegion((TextureRegion) animation.getKeyFrame(animationTime, true));

     }

    private void rocketAnim() {
        if(rocketAnim.equals(RocketCond.right)){
            randomTime = 3;
        } else  if(rocketAnim.equals(RocketCond.left)){
            randomTime = 4;
        } else  if(rocketAnim.equals(RocketCond.down)){
            randomTime = 2;
        } else  if(rocketAnim.equals(RocketCond.up)){
            randomTime = 5;
        }else  if(rocketAnim.equals(RocketCond.layDown)){
            randomTime = 0;
        }else  if(rocketAnim.equals(RocketCond.standUp)){
            randomTime = 1;
        }
    }

    public void dispose()
    {if( currentFrame.getTexture()!= null)
        currentFrame.getTexture().dispose();
    }

    void setCertainFrame(boolean certainFrame) {
        isCertainFrame = certainFrame;
    }


    private void changeRocket(){

        int rnd = MathUtils.random(0,100);
        Grid2d map2d = new Grid2d(mapArr, false);
        Vector2 vector2 = new Vector2();
        vector2.set(MathUtils.random(0,13), MathUtils.random(0, 25));

        if( rnd <= 20){
            while (mapArr[(int)vector2.x][(int)vector2.y] == -1){
                vector2.set(MathUtils.random(6,13), MathUtils.random(0, 25));
                System.out.println(vector2);
            }
            path =  map2d.findPath((int)getX()/mapCoorinateCorrector,(int)getY()/mapCoorinateCorrector,
                    (int)vector2.y, (int)vector2.x);
        } else if(rnd <= 80){
            rocketAnim = RocketCond.layDown;
        } else {
            rocketAnim = RocketCond.standUp;
        }
    }

    private void Walk() {
        setX(getX() + speed.x);
        setY(getY() + speed.y);
        speed.setZero();

        if(path != null)
            if(!path.isEmpty()) {
                if (path.get(0).x > getX() / MyGdxGame.mapCoorinateCorrector) {
                    speed.x = 2.5f;
                } if (path.get(0).x < getX() / MyGdxGame.mapCoorinateCorrector) {
                    speed.x = -2.5f;
                } if (path.get(0).y > getY() / MyGdxGame.mapCoorinateCorrector) {
                    speed.y = 2.5f;
                } if (path.get(0).y < getY() / MyGdxGame.mapCoorinateCorrector) {
                    speed.y = -2.5f;
                }
                if (path.get(0).x == (int) getX() / MyGdxGame.mapCoorinateCorrector && path.get(0).y == (int) getY() / MyGdxGame.mapCoorinateCorrector) {
                    path.remove(0);
                    changeRocket();
                }
            }
    }

}
