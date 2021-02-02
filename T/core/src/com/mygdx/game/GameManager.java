package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

import java.util.Map;

/*
public class GameManager {
    private static GameManager ourInstance = new GameManager();

    public PlayerStats gameData;
    private Json json = new Json();
    private FileHandle fileHandle = Gdx.files.local("bin/GameData.json");
    private GameManager() {}

    public void initializeGameData() {
        if (!fileHandle.exists()) {
            gameData = new PlayerStats();
            saveData(gameData);
        } else {
            loadData();
        }
    }

    public void saveData(PlayerStats gameData) {
        if (gameData != null) {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)),
                    false);
        }
    }

    public void loadData() {
        gameData = json.fromJson(PlayerStats.class,
                Base64Coder.decodeString(fileHandle.readString()));
    }
    public static GameManager getInstance() {
        return ourInstance;
    }
}
*/
