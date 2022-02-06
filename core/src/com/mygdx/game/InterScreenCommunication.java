package com.mygdx.game;

import com.mygdx.game.model.CompetitionOpponent;

import java.util.List;

public interface InterScreenCommunication {

    void showToast(String msg);

    void showPlayerList(List<CompetitionOpponent> playerList);

    void openMap();

    void openGym();

    void openRoom();

    void openShop();

    void openWork();

    void openPark();

    void openCompetition();
}
