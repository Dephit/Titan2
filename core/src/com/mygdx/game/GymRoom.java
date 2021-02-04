package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GymRoom extends BaseRoom{

    public GymRoom() {
        super("gym");
    }

    public GymRoom(Player player) {
        super("gym", player);
    }

}


