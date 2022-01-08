package com.mygdx.game.rooms;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Preffics;

public class RoomRoom extends BaseRoom {


    public RoomRoom() {
        super("room");
    }

    public RoomRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "room", player);
        //Npc npc = new Npc("player2");
        //npc.clearPath();
        //npc.setPeriodicEvent();
        /*npcs.add(npc);
        objectGroup.addActor(npc);*/
        hudGroup.addActor(player.getHealthBar());
        hudGroup.addActor(player.getEnergyBar());

        player.setPlayerPostion(700, 250);
    }


    @Override
    protected InputListener getEventListener(String text, Runnable onFinish) {
        return new InputListener() {
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


    public void setCommonButtons(){
        super.setCommonButtons();
        addButton("sleepButton","empty", "",  420, 355, 250, 100, 1f,
                () -> player.setPath(550 , 300, 561, 366, PlayerCondition.sleeping));
        addButton("pcSittingBut","empty",   "", 800, 120, 250, 150, 1f,
                () -> player.setPath(800 , 250, 905, 188, PlayerCondition.pcSitting));
        addButton("refButton","empty",   "",780, 580, 150, 200, 1f,
                () -> player.setPath(900 , 500, 900, 500, PlayerCondition.stay, this::showRefrigerator));

    }

    private void showRefrigerator() {
        pause = true;
        Group group = new Group();
        Runnable runnable = ()->{
            player.setPath(900 , 500, 900, 500, PlayerCondition.stay);
            pause = false;
            group.clear();
            group.remove();
        };
        final TextButton textButton = getTextButton("pullUpOrPushUp", "refWindow", "",
                1300, 50, 450, 650, 1, runnable
        );

        final TextButton yes = getTextButton("potato", "potato", "",
                1350, 555, 165, 125, 1f, runnable);
        final TextButton no = getTextButton("nuggets", "nuggets", "",
                1300 + 450 - 215, 555, 165, 125, 1f, runnable);
        final TextButton closeRef = getTextButton("closeRef", "closeRef", "Close me!!",
                1350,65, 350, 60, 1f, runnable);
        group.addActor(textButton);
        group.addActor(yes);
        group.addActor(closeRef);
        group.addActor(no);
        buttonGroup.addActor(group);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    protected void orderExceptions(int i, int j) {
        if (objectGroup.getChildren().get(i).getName() != null){
            if(objectGroup.getChildren().get(i).getName().contains("player")) {
                Npc pl = (Npc) objectGroup.getChildren().get(i);
                if ((pl.playerCondition.equals(PlayerCondition.pcSitting)))
                    if(pl.getName().equals("player"))
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 1);
                    else
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 2);
            }
            super.orderExceptions(i,j);
        }
    }
}

