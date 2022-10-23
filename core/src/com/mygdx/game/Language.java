package com.mygdx.game;

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
    public String creatineTitle;
    public String itemDescriptions;
    public String itemBoostDescription;
    public String itemBoostDescriptions;
    public String itemAddDescription;
    public String proteinBar;
    public String energyDrink;
    public String potato;
    public String nuggets;
    public String silerKneeSleaves;
    public String basikKneesleaves;
    public String goldenKneeSleaves;
    public String sdbKneesleaves;
    public String platinumKneesleaves;
    public String silverWristWraps;
    public String redWristWraps;
    public String platinumWristWraps;
    public String goldenWristWraps;
    public String blackWristWraps;
    public String basicWristWraps;
    public String b12BeltTitle;
    public String izerBelt;
    public String goldenBelt;
    public String basicBelt;
    public String sbdBelt;
    public String wristUsePerk;
    public String wristUsePerkDesc;
    public String squaterPerk;
    public String squaterPerkDescription;
    public String squatThirdPerk;
    public String squaterSecondPerk;
    public String squaterFirstPerk;

    public String squatImprovePerk;
    public String squatEnergyImprovePerk;
    public String squatEnergyFirstPerk;
    public String kneesleavesUsePerk;
    public String beltUsePerk;
    public String beltUsePerkDescription;

    public String deadliftSecondPerk;
    public String deadliftThirdPerk;
    public String deadliftFirstPerk;
    public String deadliftPerk;
    public String deadliftImprovePerk;
    public String bencherPerk;
    public String bencherIPerk;
    public String bencherIIPerk;
    public String bencherIIIPerk;
    public String bencherImprovePerk;
    public String youCantBuyThisPerkYet;
    public String youDontHaveEnoughMoney;
    public String youAlreadyHaveThisPerk;

    Language(){

    }

    static Language getLanguage(String path){
        return json.fromJson(Language.class, path);
    }

    public static String getString(String title, String... text) {
        return String.format(title, text);
    }
}
