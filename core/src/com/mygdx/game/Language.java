package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

class Language{

    public String loading;
    public String heavySquat;
    public String moderateSquat;
    public String easySquat;
    public String mockSquat;

    public String heavyBench;
    public String moderateBench;
    public String easyBench;
    public String mockBench;

    public String heavyDeadlift;
    public String moderateDeadlift;
    public String easyDeadlift;
    public String mockDeadlift;


    public List<String> dumbelGuyRandomText;
    public List<String> armGirlDialogTree;
    public List<String> armGirlRandomText;
    public List<String> coachDialogTree;
    public List<String> coachRandomText;
    public List<String> sanitarRandomText;
    public List<String> stuffRandomText;
    public String chooseYourExercise;

    Language(){

    }

    static Language getLanguage(String path){
        return json.fromJson(Language.class, path);
    }
}
