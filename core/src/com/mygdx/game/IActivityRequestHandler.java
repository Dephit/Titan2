package com.mygdx.game;

import com.mygdx.game.interfaces.OnCLickCallback;
import com.mygdx.game.interfaces.OnClickBooleanCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.enums.Comp;

import java.util.List;

public interface IActivityRequestHandler {
    void showToast(String s);

    void showPlayers(List<CompetitionOpponent> playerList, Comp status, OnCLickCallback runnable);

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

    void openOptions();

    void showHud(Player player);

    void openInventory(Player player, Runnable runnable);

    void openRefrigerator(Player player, Runnable onClose);

    void openShopByMenu(Container container, OnCLickCallback onBuyRunnable, Runnable runnable);

    void openStats(Player player, Runnable pauseGame);

}
