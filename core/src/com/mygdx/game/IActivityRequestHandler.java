package com.mygdx.game;

import com.mygdx.game.interfaces.OnClickCallback;
import com.mygdx.game.interfaces.OnClickBooleanCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.enums.Comp;
import com.mygdx.game.model.enums.InventoryType;
import com.mygdx.game.model.items.OnItemClick;

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

    void openInventory(Player player, Runnable runnable, OnItemClick onItemClick);

    void openRefrigerator(Player player, OnItemClick onItemClick, Runnable onClose);

    void openShowBuyMenu(InventoryType type, Container inventoryContainer, Container shopContainer, OnItemClick onBuyRunnable, Runnable onCancel);

    void openStats(Player player, Runnable pauseGame);

    void openPerkMenu(Player player, Runnable pauseGame);

    void savePlayer(String toString);

    void showAd(Runnable onAdClosed);

}
