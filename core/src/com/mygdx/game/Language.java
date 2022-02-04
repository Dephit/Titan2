package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class Language{

    public String loading;
    public String heavySquat;
    public String moderateSquat;
    public String easySquat;
    public String mockSquat;

    public String bestResult;
    public String level;

    public String squat;
    public String bench;
    public String deadlift;

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
    public String refregiratorIsFull;
    public String buyText;
    public String no;
    public String thisIsTaken;
    public String wannaWork;
    public String forThisWorkYouRecieve;
    public String doWork;
    public String cancel;
    public String workInProgress;
    public String comtetitionTitle;
    public String squatTitle;
    public String nextSet;
    public String youHaveNoEnergyOrHealth;
    public String walkInThePark;
    public String walkToRecoverYourHead;
    public String doWalk;
    public String walkingInProgress;
    public String health;
    public String energy;
    public String moral;
    public String set;

    Language(){

    }

    static Language getLanguage(String path){
        return json.fromJson(Language.class, path);
    }
}
