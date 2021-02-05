package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

class Language{

    public String loading;
    public List<String> dumbelGuyRandomText;
    public List<String> armGirlDialogTree;
    public List<String> armGirlRandomText;
    public List<String> coachDialogTree;
    public List<String> coachRandomText;
    public List<String> sanitarRandomText;
    public List<String> stuffRandomText;

    Language(){

    }

    static Language getLanguage(String path){
        return json.fromJson(Language.class, path);
    }
}
