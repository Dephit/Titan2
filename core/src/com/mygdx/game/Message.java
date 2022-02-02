package com.mygdx.game;

/*
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import static com.mygdx.game.MyMethods.LoadImg;
import static com.mygdx.game.MyMethods.getJson;
import static com.mygdx.game.MyMethods.getTextButtonStyleFromFile;
import static com.mygdx.game.MyMethods.textDrawing;

public class Message extends Actor{
    private int metaX;
    String text, name;
    ArrayList<String> randomText;
    ArrayList<String> dialogTree;
    private boolean flip = false;
    private int x, y, textX, textY;
    private float width = 0, height = 0, mult = 1.5f;
    private int curDialog = 0, allDialogs = 0;
    private Texture message=LoadImg("screens/dialogs/message.png");
    private boolean showDialog;
    private StringAlignUtils util;

    Message(int x, int y, String text, boolean flip) {

        this.x = x;
        this.y = y;
        this.text = text;
        this.flip = flip;
        manageSize();

    }

    Message(MessageData messageData) {
        name = messageData.buttonName;
        randomText = messageData.RandomText;
        dialogTree = messageData.dialogTree;
        metaX = x;
        x = messageData.x;
        y = messageData.y;
        text = "";
        flip = messageData.flip;
        curDialog = 0;
        manageSize();
    }

    private void manageSize() {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(FontFactory.font, text);
        width = layout.width;
        height = layout.width / 2;
        if(!flip)
            textX = (int) (x + (width * 0.95f) * mult / 2 - width / 2);
        else textX = (int) (x + (width * 1.05f) * mult / 2 - width / 2);
        textY = (int) (y + (height / 3) + height / 2);
       //private StringAlignUtils util; util = new StringAlignUtils(20, StringAlignUtils.Alignment.RIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(Gdx.input.isTouched()){
            this.remove();
            stopIt();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!flip){
            batch.draw(message, x - (width * mult) , y,  width * mult, height * mult );
            textDrawing(batch, */
/*util.format(text)*//*
 text, textX - (width * mult), textY,1f, Color.BLACK);
        }
        else{
            batch.draw(message, x + width * mult, y ,  -width * mult, height * mult );
            textDrawing(batch, */
/*util.format(text)*//*
 text, textX, textY,1f, Color.BLACK);
        }
    }

    void nextDialog() {
        //if(showDialog) {
            allDialogs = dialogTree.size() ;
            if (curDialog < allDialogs)
                text = dialogTree.get(curDialog);
            else
                text = randomText.get(MathUtils.random(0, randomText.size() - 1));
            manageSize();
           // }
        }

    private void stopIt(){
        if (curDialog < allDialogs){
            curDialog++;
        }
    }
}*/


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message extends BaseActor {

    private Runnable runnable;
    String name;
    private Texture texture;
    private float width, height, mult = 1.5f;
    String buttonName;
    int x,y;
    boolean flip;
    List<String> dialogTree = new ArrayList<String> ();
    List <String> randomText = new ArrayList<String> ();
    private StringAlignUtils util;
    private String text;
    private int textX, textY;
    private int currentDialog = 0;

    private void init(int x, int y, boolean flip, List<String> tree, List<String> random) {
        this.x = x;
        this.y = y;
        dialogTree = tree;
        randomText = random;
        this.flip = flip;
        setName("message");
        name = getName();
        util = new StringAlignUtils(50, StringAlignUtils.Alignment.LEFT);
        texture = Preffics.getInstance().getByPath("screens/dialogs/message.png", Texture.class);
        manageSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return name.equals(message.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Message(int x, int y, boolean flip, List<String> tree, List<String> random) {
        init(x,y,flip, tree, random);
    }

    public Message(int x, int y, boolean flip, List<String> tree, List<String> random, int currentDialog, Runnable afterClickRunnable) {
        this.currentDialog = currentDialog;
        this.runnable = afterClickRunnable;
        init(x, y, flip, tree, random);
    }

    @Override
    public boolean remove() {
        runnable = null;
        return super.remove();
    }

    private void manageSize() {
        if(!dialogTree.isEmpty() && currentDialog < dialogTree.size()){
            setData(dialogTree.get(currentDialog));
            currentDialog ++;
            if(runnable != null)
                runnable.run();
        }else if(!randomText.isEmpty()){
            setData(randomText.get(new Random().nextInt(randomText.size())));
        }
    }

    private void setData(String dialog) {
        text = dialog;
        int l = text.split("\n").length;
        int textHeight =  (l > 0 ? l : 1) * 72 + 96;
        int textWidth = getMaxLineLenght(text) * 48;

        width = textWidth;
        height = textHeight;
        textX = (int) (x + width / 5);
        textY = y + l * 48 + 48;
    }

    private int getMaxLineLenght(String text) {
        int maxLine = 0;
        for (String s : text.split("\n")) {
            if(s.length() > maxLine)
                maxLine = s.length();
        }
        return maxLine;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(!flip){
            batch.draw(texture, x, y,  width, height);
        } else{
            batch.draw(texture, x + width, y ,  -width, height);
        }
        showText(batch, text, textX, textY,2f, Color.BLACK);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(Gdx.input.isTouched()){
            if(currentDialog < dialogTree.size()){
                manageSize();
            }else
                remove();
        }
    }
}


