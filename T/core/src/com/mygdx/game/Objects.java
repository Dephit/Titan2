package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.MyMethods.*;

public class Objects extends Actor
{
    public   String imgName;
    public Animation animation;
    public int rows,colls;
    private float animationTime;
    private float time,x,y,width,height;
    private TextureRegion currentFrame;

    public Objects(String s, String name) {
    imgName=getDirectoryFiles(s,name)[0];
    setName(name);
    setBounds(getFileParam(imgName,".X"), getFileParam(imgName,".Y"),
            getFileParam(imgName,".W"), getFileParam(imgName,".H"));
    rows=getFileParam(imgName,".R");
    colls=getFileParam(imgName,".C");
    time=getFileParam(imgName,".T");
    animationTime=0;
    animation=MyMethods.createAnimation(s+imgName,colls,rows,1f);
}

    public Objects(String path,MyGdxGame.ObjectData objectData) {
        imgName=getDirectoryFiles(path,objectData.name)[0];
        setName(objectData.name);
        setBounds(objectData.x, objectData.y, objectData.width, objectData.height);
        rows=objectData.rows;
        colls=objectData.cols;
        time=objectData.time;
        animationTime=0;
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
        animationTime+=delta*time;
        if(animationTime>rows*colls)animationTime=0;
    }

    public void dispose()
    {if( currentFrame.getTexture()!=null)
        currentFrame.getTexture().dispose();
    }
}
