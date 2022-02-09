package com.mygdx.game;

import com.mygdx.game.interfaces.OnCLickCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.enums.Comp;

import java.util.List;
import java.util.Map;

public interface IActivityRequestHandler {
    public void showAds(boolean show);
    public boolean isAdShown();
    public void signOut();
    public void saveData(Map value);
    public Map loadData();
    public void showToast(String s);

    public void showPlayers(List<CompetitionOpponent> playerList, Comp status, OnCLickCallback runnable);

    public void showNextSetMenu(
            Player playerList,
            int currentSet,
            OnCLickCallback onFirstClick,
            OnCLickCallback onSecondClick,
            OnCLickCallback onThirdClick,
            OnCLickCallback onFourthClick
    );
}
