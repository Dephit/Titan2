package com.mygdx.game;

import java.util.ArrayList;

public class ButtonData {
    public String name;
    public int x, y, width, height;
    ArrayList<String[]> screens = new ArrayList<String[]>(); // There are should be 5 items in an array: name, x, y, width, height
    //in same order, and all String, so you should consider parsing to int
    public ButtonData() {
    }
}
