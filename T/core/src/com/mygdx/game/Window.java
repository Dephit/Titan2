package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import static com.mygdx.game.MyMethods.createAnimation;
import static com.mygdx.game.MyMethods.getJson;

public class Window extends Actor{

    private final Animation animation;
    public Group thisGroup;

    Window(float x, float y, float width, float height, String name) {
        thisGroup=new Group();
        setBounds(x,y,width,height);
        animation=createAnimation("screens/windows/window.png",1,1,1);
    }

    public void loadButtons(String name, ArrayList<TextButton> list) {
       // TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal(getPath() + "screens/windows/" + name +"/buttons.atlas"));
        String buttonList = getJson( "screens/windows/" + name +"/buttons.json");

        final ArrayList<MyGdxGame.ButtonData> buttonDataArrayList= json.fromJson(  ArrayList.class, buttonList);

        for (final MyGdxGame.ButtonData buttonData : buttonDataArrayList) {
            for (TextButton textButton : list) {
                if(buttonData.name.equals(textButton.getName())){
                    textButton.setBounds(buttonData.x,buttonData.y,buttonData.width,buttonData.height);
                    thisGroup.addActor(textButton);
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) animation.getKeyFrame(1),getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose(){

    }

}
