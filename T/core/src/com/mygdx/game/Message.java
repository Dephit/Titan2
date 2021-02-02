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
       // util = new StringAlignUtils(20, StringAlignUtils.Alignment.RIGHT);
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

    public static class MessageData {
        String buttonName;
        int x,y;
        boolean flip;
        ArrayList < String > dialogTree = new ArrayList < String > ();
        ArrayList < String> RandomText = new ArrayList< String>();
        //ArrayList<ArrayList<String>> RandomText = new ArrayList<>();

        public MessageData() {

        }

    }


}*/
