package com.mygdx.game;

import com.mygdx.game.interfaces.OnClickCallback;
import com.mygdx.game.interfaces.OnClickBooleanCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.enums.Comp;

import java.util.List;

public interface IActivityRequestHandler {
    void showToast(String s);

    void showPlayers(List<CompetitionOpponent> playerList, Comp status, OnClickCallback runnable);

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

    void openOptions();

    void showHud(Player player);

    void openInventory(Player player, Runnable runnable);

    void openRefrigerator(Player player, Runnable onClose);

    void openShopByMenu(Container container, OnClickCallback onBuyRunnable, Runnable runnable);

    void openStats(Player player, Runnable pauseGame);

    void openPerkMenu(Player player, Runnable pauseGame);

    void savePlayer(String toString);

    void showAd(Runnable onAdClosed);
}
