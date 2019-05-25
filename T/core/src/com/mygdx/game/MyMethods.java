package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.XmlWriter;


import java.awt.Font;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

public abstract class MyMethods {

    public static Texture LoadImg(String path) {
        return  new Texture(getPath()+path);
    }

    public static Pixmap createProceduralPixmap (int width, int height,float r,float g,float b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fill();
        return pixmap;
    }

    public static String[] getDirectoryFiles(String path) {
        String[] ReturnArray;
        FileHandle dirHandle;

        dirHandle = Gdx.files.internal(getPath()+path);

        ReturnArray=new String[dirHandle.list().length];
        int i=0;
        for (FileHandle entry: dirHandle.list()) {
            ReturnArray[i] = entry.name();
            i++;
        }
        return ReturnArray;
    }

    public static int getFileParam(String ImgName,String parameter){
        if(ImgName.contains(parameter)){
            StringBuilder string= new StringBuilder();
            char c='1';
            for (int i = ImgName.indexOf(parameter)+2; i < ImgName.length()-1; i++) {
                c=ImgName.charAt(i);
                string.append(ImgName.charAt(i));
                if (ImgName.charAt(i+1)=='.')
                    return Integer.valueOf(String.valueOf(string));
            }
            return Integer.valueOf(String.valueOf(string));
        }
        return 1;
    }

    public static String[] getDirectoryFiles(String path, String Searcher) {
        String[] ReturnArray;
        FileHandle dirHandle;
        dirHandle = Gdx.files.internal(getPath()+path);

        int i=0;
        for (FileHandle entry: dirHandle.list()) {
            if(entry.name().contains(Searcher)) i++;
        }
        ReturnArray=new String[i];
        i=0;
        for (FileHandle entry: dirHandle.list()) {
            if(entry.name().contains(Searcher)) {
                ReturnArray[i] = entry.name();
                i++;
            }
        }
        return ReturnArray;
    }

    public static String getPath(){
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            return "";
        else
            return "android/assets/";
    }

    public static String getJson(String path){
        FileHandle file = Gdx.files.internal(getPath()+path);
        String json = file.readString();
        return json;
    }
    //Add possible to add colors from json
    public static TextButton.TextButtonStyle getTextButtonStyleFromFile(Skin skin, String name){
        final TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontFactory.font;
        textButtonStyle.fontColor = Color.BLACK;

        textButtonStyle.downFontColor = Color.BLUE;
        textButtonStyle.up = skin.getDrawable(name);
        textButtonStyle.down = skin.getDrawable(name);
        skin.dispose();
        return textButtonStyle;
    }

    public static void sort_array(ArrayList<Actor> list,float new_x,float new_y){
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPosition(new_x, new_y-(list.get(i).getHeight()+10)*i);
        }
    }

    public static void textDrawing(Batch batch, String str, float x, float y, float scale,Color color) {
        FontFactory.getInstance().getFont(MyGdxGame.locale).getData().setScale(scale);
        FontFactory.getInstance().getFont(MyGdxGame.locale).setColor(color);
        FontFactory.getInstance().getFont(MyGdxGame.locale).draw(batch, str, x,y);
        FontFactory.getInstance().getFont(MyGdxGame.locale).getData().setScale(1);
        FontFactory.getInstance().getFont(MyGdxGame.locale).setColor(Color.BLACK);
    }

    public static String format(String format, Object var){
            return String.format(format,var);
    }

    public static Animation createAnimation(String path, int Collumns, int Rows, float Frame_duraction){
        Texture texture = MyMethods.LoadImg(path);
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / Collumns,
                texture.getHeight() / Rows);
        TextureRegion[] walkFrames = new TextureRegion[Collumns * Rows];
        int index = 0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Collumns; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        return new Animation<TextureRegion>(Frame_duraction, walkFrames);
    }

}
