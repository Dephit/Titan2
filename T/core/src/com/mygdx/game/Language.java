package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

class Language{

    String loading;

    Language(){

    }

    static Language getLanguage(String path){
        return json.fromJson(Language.class, path);
    }
}
