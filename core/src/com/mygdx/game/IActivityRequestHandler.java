package com.mygdx.game;

import com.mygdx.game.interfaces.OnCLickCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.enums.Comp;

import java.util.List;
import java.util.Map;

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
}
