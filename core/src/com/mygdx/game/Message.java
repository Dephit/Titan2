package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {

    List<String> dialogTree;
    List <String> randomText;

    public String text;
    private int currentDialog = 0;

    public Message(String name, List<String> tree, List<String> random) {
        dialogTree = tree;
        randomText = random;
        manageSize();
    }

    private void manageSize() {
        if(!dialogTree.isEmpty() && currentDialog < dialogTree.size()){
            setData(dialogTree.get(currentDialog));
            currentDialog ++;
        }else if(!randomText.isEmpty()){
            setData(randomText.get(new Random().nextInt(randomText.size())));
        }

    }

    private void setData(String dialog) {
        text = dialog;
    }


    public void onClick(){
        manageSize();
    }
}


