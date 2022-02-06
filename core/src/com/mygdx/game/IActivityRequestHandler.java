package com.mygdx.game;

import com.mygdx.game.model.CompetitionOpponent;

import java.util.List;
import java.util.Map;

public interface IActivityRequestHandler {
    public void showAds(boolean show);
    public boolean isAdShown();
    public void signOut();
    public void saveData(Map value);
    public Map loadData();
    public void showToast(String s);

    public void showPlayers(List<CompetitionOpponent> playerList);
}
