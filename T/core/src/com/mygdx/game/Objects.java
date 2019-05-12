package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.MyMethods.*;

public class Objects extends Actor
{
    public   String imgName;
    public Animation animation;
    public int rows,colls;
    private float animationTime;
    private float time,x,y,width,height;
    private TextureRegion currentFrame;
    private List<int[]> animations=new ArrayList<int[]>(); // int[0] - StartFrame, int[1]-LastFrame, int[2]-AnimTime, int[3]-Chance
    private int randomTime;

    Objects(String path, MyGdxGame.ObjectData objectData) {
        imgName=getDirectoryFiles(path,objectData.name)[0];
        setName(objectData.name);
        setBounds(objectData.x, objectData.y, objectData.width, objectData.height);
        rows=objectData.rows;
        colls=objectData.cols;
        time=objectData.time;
        animationTime=0;

        try{
            animations = objectData.animations;
            randomTime=randomAnim();
        }catch (IllegalArgumentException e){
            animations.add(new int[]{0, rows * colls, (int) time, 100});
            randomTime=randomAnim();
        }

        animation=MyMethods.createAnimation(path+imgName,colls,rows,1f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        currentFrame = (TextureRegion) animation.getKeyFrame(animationTime, true);
        batch.draw(currentFrame,getX(),getY(),getWidth(),getHeight());
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
                    System.out.print(animations.get(i)[3]+"   ");
                return i;
            }
        }
        return MathUtils.random(0,animations.size()-1);
    }

    private void doRandomAnim(float delta) {
        animationTime+=delta*animations.get(randomTime)[2] ;
        if(animationTime > animations.get(randomTime)[1] || animationTime < animations.get(randomTime)[0]){
            randomTime= randomAnim();
            animationTime=animations.get(randomTime)[0];
        }
    }

    public void dispose()
    {if( currentFrame.getTexture()!=null)
        currentFrame.getTexture().dispose();
    }
}
