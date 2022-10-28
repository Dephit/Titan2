package com.mygdx.game;

import com.mygdx.game.interfaces.OnClickCallback;
import com.mygdx.game.interfaces.OnClickBooleanCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.enums.Comp;

import java.util.List;

public interface InterScreenCommunication {

    void showToast(String msg);

    void showPlayerList(List<CompetitionOpponent> playerList, Comp status, OnClickCallback runnable);

    void showNextSetMenu(
            Player player,
            int currentSet,
            List<CompetitionOpponent> playerList,
            OnClickCallback onFirstClick,
            OnClickCallback onClose
    );

    void showDialog(
            String title,
            String subtitle,
            OnClickCallback onClose,
            OnClickCallback onAgree
    );

    void showProgressBar(
            String title,
            OnClickCallback onProgressEnd,
            OnClickBooleanCallback onProgressUpdate,
            OnClickCallback onClose);


    void showDialog(
            String title,
            String subtitle,
            String agreeText,
            String closeText,
            OnClickCallback onClose,
            OnClickCallback onAgree
    );

    void openMap();

    void openGym();

    void openRoom();

    void openShop();

    void openInventory(Runnable runnable);

    void openWork();

    void openPark();

    void openCompetition();

    void openOptions();

    void openRefrigerator(Player player, Runnable runnable);

    void showBuyMenu(Container container, OnClickCallback onBuyRunnable, Runnable runnable);

    void showHud();

    void openStats(Runnable pauseGame);

    void openPerkMenu(Runnable pauseGame);

    void savePlayer(String toString);
}


