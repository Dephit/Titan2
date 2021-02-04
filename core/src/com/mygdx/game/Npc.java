package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.LinkedList;
import java.util.List;

public class Npc extends Actor {

    public List<Grid2d.MapNode> path = new LinkedList<Grid2d.MapNode>();
    protected Grid2d map2d;

    public void setPath(List<Grid2d.MapNode> path) {
        this.path = path;
    }

    void ceilPos() {
        setPosition(getX() % 10 != 5f ? getX() - getX() % 10 : getX(), getY() % 10 != 5 && getY() % 10 != 0 ? getY() - getY() % 10 : getY());
    }

    public void clearPath() {
        if (path != null)
            path.clear();
        map2d = new Grid2d(Preffics.getInstance().mapArr, false);
    }
}

