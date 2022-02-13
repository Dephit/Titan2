package com.mygdx.game.rooms;

import static com.mygdx.game.PlayerCondition.lookinLeft;
import static com.mygdx.game.PlayerCondition.lookinUp;
import static com.mygdx.game.PlayerCondition.stay;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.Exercise;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Message;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Preffics;

import java.util.ArrayList;
import java.util.Map;

public class MapRoom extends BaseRoom {

    public MapRoom() {
        super("map");
    }

    public MapRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "map", player);
        player.setPlayerPosition(-400,100);
        player.setPlayersAction(stay, -400, 100, ()->{});
    }


    public void setCommonButtons(){
        super.setCommonButtons();
        addButton("shop", "shop","", 710, 650, 125, 125, 1f, ()-> interScreenCommunication.openShop());
        addButton("gym", "gym","", 800, 860, 125, 125, 1f, ()-> interScreenCommunication.openGym());
        addButton("room", "room","", 500, 350, 125, 125, 1f, ()-> interScreenCommunication.openRoom());
        addButton("work", "workButton","",  950, 200, 110, 105, 1f, ()-> interScreenCommunication.openWork());
        addButton("work", "map","",  450, 200, 110, 105, 1f, ()-> interScreenCommunication.openPark());
        if(player.day.currentDay % 5 == 0) {
            addButton("competition", "champ", "", 1600, 330, 110, 105, 1f, () -> interScreenCommunication.openCompetition());
        }
    }

    @Override
    protected InputListener getEventListener(String text, Runnable onFinish) {
        return  new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                onFinish.run();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    protected void orderExceptions(int i, int j) {
        /*if (objectGroup.getChildren().get(i).getName() != null){
            if(objectGroup.getChildren().get(i).getName().contains("player")) {
                Npc pl = (Npc) objectGroup.getChildren().get(i);
                if ((pl.playerCondition.equals(PlayerCondition.bench) ||
                        pl.playerCondition.equals(PlayerCondition.pullUps) ||
                        pl.playerCondition.equals(PlayerCondition.legPress) ||
                        pl.playerCondition.equals(PlayerCondition.sitting) ||
                        pl.playerCondition.equals(PlayerCondition.sittingRev) ||
                        pl.playerCondition.equals(PlayerCondition.hiper) ||
                        pl.playerCondition.equals(PlayerCondition.pushUps))||
                        pl.playerCondition.equals(PlayerCondition.pcSitting))
                    if(pl.getName().equals("player"))
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 1);
                    else
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 2);
            }
            super.orderExceptions(i,j);
        }*/
    }
}



