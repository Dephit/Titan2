package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

public class Npc extends Actor {

    protected List<Grid2d.MapNode> path;
    protected int mapCoordinateCorrector = 50;

    public void setPath(List<Grid2d.MapNode> path) {
        this.path = path;
    }

    void ceilPos() {
        setPosition(getX() % 10 != 5f ? getX() - getX() % 10 : getX(), getY() % 10 != 5 && getY() % 10 != 0 ? getY() - getY() % 10 : getY());
    }
}

