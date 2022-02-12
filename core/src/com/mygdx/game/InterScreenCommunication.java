package com.mygdx.game;

import com.mygdx.game.interfaces.OnCLickCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.enums.Comp;

import java.util.List;

public interface InterScreenCommunication {

    void showToast(String msg);

    void showPlayerList(List<CompetitionOpponent> playerList, Comp status, OnCLickCallback runnable);

    void showNextSetMenu(
            Player player,
            int currentSet,
            List<CompetitionOpponent> playerList,
            OnCLickCallback onFirstClick,
            OnCLickCallback onClose
    );

    void openMap();

    void openGym();

    void openRoom();

    void openShop();

    void openWork();

    void openPark();

    void openCompetition();
}


