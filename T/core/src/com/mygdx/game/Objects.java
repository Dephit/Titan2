package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

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
    private int randomTime;

    Objects(String path, MyGdxGame.ObjectData objectData) {
        String imgName = getDirectoryFiles(path, objectData.name)[0];
        setName(objectData.name);
        setBounds((int)objectData.x, (int)objectData.y, (int)objectData.width , (int)objectData.height );
        int rows = objectData.rows;
        int colls = objectData.cols;
        int time = objectData.time;
        animationTime=0;

        try{
            animations = objectData.animations;
            randomTime=randomAnim();
        }catch (IllegalArgumentException e){
            animations.add(new int[]{0, rows * colls, time, 100});
            randomTime=randomAnim();
        }
       // Animation<TextureRegion> animation = new Animation<TextureRegion>(0.033f, atlas.findRegions("running"),true);


        animation=MyMethods.createAnimation(path + imgName, colls, rows,1f);
        currentFrame = (TextureRegion) animation.getKeyFrame(animationTime);
    }

    public Objects(String path, MyGdxGame.ObjectData objectData, TextureAtlas textureAtlas) {
        String imgName = getDirectoryFiles(path, objectData.name)[0];
        setName(objectData.name);
        setBounds((int)objectData.x, (int)objectData.y, (int)objectData.width , (int)objectData.height );
        int rows = objectData.rows;
        int colls = objectData.cols;
        int time = objectData.time;
        animationTime=0;

        try{
            animations = objectData.animations;
            randomTime=randomAnim();
        }catch (IllegalArgumentException e){
            animations.add(new int[]{0, rows * colls, time, 100});
            randomTime=randomAnim();
        }

        TextureRegion[][] tmp = textureAtlas.findRegion(getName()).split((int) (getWidth() / 5), (int) (getHeight() / 5));

        TextureRegion[] walkFrames = new TextureRegion[colls * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colls; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<TextureRegion>(1f, walkFrames);
        currentFrame = (TextureRegion) animation.getKeyFrame(animationTime);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        currentFrame.setRegion((TextureRegion) animation.getKeyFrame(animationTime,true));
        batch.draw(currentFrame, getX(), getY() , getWidth() ,getHeight() );
       /* System.out.println(getName() + ' ' + getX()+ ' ' + getX()+ ' ' + getWidth()+ ' ' + getHeight()+ ' '  );
        System.out.println("w "+getStage().getWidth()+ " h" + getStage().getHeight()+ ' ');
        System.out.println("rw "+Gdx.graphics.getWidth()+ " rh" + Gdx.graphics.getHeight()+ ' ');*/
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
        animationTime += delta* animations.get(randomTime)[2] ;
        if(animationTime > animations.get(randomTime)[1] || animationTime < animations.get(randomTime)[0]){
            randomTime = randomAnim();
            animationTime = animations.get(randomTime)[0];
       }
    }

    public void dispose()
    {if( currentFrame.getTexture()!= null)
        currentFrame.getTexture().dispose();
    }
}
