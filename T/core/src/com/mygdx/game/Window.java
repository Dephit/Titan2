package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import static com.mygdx.game.MyMethods.createAnimation;
import static com.mygdx.game.MyMethods.getJson;
import static com.mygdx.game.MyMethods.textDrawing;

public class Window extends Actor{

    private final Animation animation;
    private final GlyphLayout layout;
    Group thisGroup;
    private String label;

    Window(float x, float y, float width, float height, String name) {
        //TODO Make window label
        thisGroup = new Group();
        //TODO unhardcore it
        label = "Выбирай!";
        layout = new GlyphLayout();
        layout.setText(FontFactory.font, label);

        set((int)x, (int)y, (int)width, (int)height,name);
        animation = createAnimation("screens/windows/window.png",1,1,1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) animation.getKeyFrame(1),getX(),getY(),getWidth(),getHeight());
        textDrawing(batch, label, getX() + getWidth() / 2 - layout.width / 2, getY() +getHeight() * 0.85f,  1, Color.BLACK);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose(){

    }

    public void set(int x, int y, int width, int height, String name) {
        thisGroup.clear();
        setBounds(x, y, width, height);
        setName(name);
    }

}
