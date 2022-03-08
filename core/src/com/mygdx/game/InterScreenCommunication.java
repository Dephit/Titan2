package com.mygdx.game;

import com.mygdx.game.interfaces.OnCLickCallback;
import com.mygdx.game.interfaces.OnClickBooleanCallback;
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

    void showDialog(
            String title,
            String subtitle,
            OnCLickCallback onClose,
            OnCLickCallback onAgree
    );

    void showProgressBar(
            String title,
            OnCLickCallback onProgressEnd,
            OnClickBooleanCallback onProgressUpdate,
            OnCLickCallback onClose);


    void showDialog(
            String title,
            String subtitle,
            String agreeText,
            String closeText,
            OnCLickCallback onClose,
            OnCLickCallback onAgree
    );

    void openMap();

    void openGym();

    void openRoom();

    void openShop();

    void openWork();

    void openPark();

    void openCompetition();
}


