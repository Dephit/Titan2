package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.Locale;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class Preffics {

    public static int SCREEN_WIDTH = 1920;
    public static int SCREEN_HEIGHT = 1080;

    public final AssetManager assetManager = new AssetManager();

    protected int mapCoordinateCorrector = 50;

    private static Preffics instance;
    private Locale locale = null;
    private Language language;
    public double[][] mapArr;
    public int mapSize;

    public static Preffics getInstance(){
        if(instance == null){
            instance = new Preffics();
            instance.choseLanguage("ru");
            FontFactory.getInstance().initialize();
        }
        return instance;
    }

    <T> Preffics load(String path, Class<T> textureClass){
        assetManager.load(getPath() + path, textureClass);
        return this;
    }

    <T> T getByPath(String path, Class<T> textureClass){
        return assetManager.get(getPath() + path, textureClass);
    }

    public void choseLanguage(String lang) {
        switch (lang) {
            case "ru":
                locale = new Locale("ru", "RU");
                break;
            default:
                locale = new Locale("es", "ES");
                break;
        }

        language = Language.getLanguage(getJson("locale/" + lang + ".json"));
    }

    public <T> T fromObjectFromJson(String path, Class<T> textureClass){
        return json.fromJson(textureClass, getJson(path));
    }

    public Locale getLocale() {
        return locale;
    }

    public String getPath() {
        return Gdx.app.getType() == Application.ApplicationType.Android ? "" : "android/assets/";
    }

    public Language getLanguage() {
        return language;
    }

    public String getJson(String path){
        FileHandle file = Gdx.files.internal(getPath()+path);
        return file.readString();
    }

    public boolean isLoaded() {
        return assetManager.isFinished();
    }

    public void updateLoading() {
        try{
            assetManager.update();
        }catch (Exception ignored){

        }
    }

    public float getLoadingProgress() {
        return assetManager.getProgress();
    }

    public void clearAssets() {
        assetManager.clear();
    }

    public void createMap(String tag) {
        mapSize = 14;
        mapCoordinateCorrector = 50;
        mapArr = new double[mapSize][mapSize * 4];
        // saveRoomMap();
        RestoreMap(tag);
    }

    public void RestoreMap(String tag) {
        try {
            ArrayList<Coordinates> coordinates = fromObjectFromJson("screens/" + tag + "/" + tag + ".json", ArrayList.class);
            for (Coordinates coordinate : coordinates) {
                mapArr[coordinate.x][coordinate.y] = -1;
            }
        } catch (GdxRuntimeException ignored) {

        }
    }

    public Texture loadImg(String s) {
        return new Texture(Preffics.getInstance().getPath() + s);
    }
}
