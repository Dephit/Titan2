package com.mygdx.game;

import java.util.Map;

public interface IActivityRequestHandler {
    public void showAds(boolean show);
    public boolean isAdShown();
    public void signOut();
    public void saveData(Map value);
    public Map loadData();
}
