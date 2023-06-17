package com.mygdx.game.rooms;

import static com.mygdx.game.PlayerCondition.stay;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.*;
import com.sun.tools.sjavac.Log;

public class HomeRoom extends BaseRoom {


    public HomeRoom() {
        super("room");
    }

    public HomeRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "room", player);
        player.setPlayerPosition(700, 250);
        player.setPlayersAction(stay, 700, 250, () -> {
        });
    }

    private final OnPlayerConditionChangeListener onPlayerConditionChangeListener = (oldPlayerCondition, playerCondition) -> {
        if (oldPlayerCondition == PlayerCondition.sleeping && playerCondition == PlayerCondition.stay) {
            //player.goToDestination(550, 256, 561, 256, stay, () -> player.postNotificationMessage("I don't want to sleep"));
        }
    };

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


    public void setCommonButtons() {
        super.setCommonButtons();
        addButton("sleepButton", Style.empty, "", 420, 355, 250, 100, 1f,
            () -> player.goToDestination(550, 300, 561, 366, PlayerCondition.sleeping));
        addButton("pcSittingBut", Style.empty, "", 800, 120, 250, 150, 1f,
            () -> player.goToDestination(800, 250, 905, 188, PlayerCondition.pcSitting));
        addButton("refButton", Style.empty, "", 780, 580, 150, 200, 1f,
            () -> player.goToDestination(900, 500, 900, 500, stay, this::showRefrigerator));

        player.setOnPlayerConditionChangeListener(onPlayerConditionChangeListener);
    }

    Group refPopupGroup = new Group();

    private void showRefrigerator() {
        Runnable pauseRunnable = pauseGame();
        Runnable runnable = () -> {
            player.goToDestination(900, 500, 900, 500, stay);
            pauseRunnable.run();
            refPopupGroup.clear();
            refPopupGroup.remove();
        };
        interScreenCommunication.openRefrigerator(
            player,
            item -> {
                item.onUse(player);
                player.inventoryManager.refrigerator.removeItem(item);
                showRefrigerator();
            },
            runnable
        );
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    protected void orderExceptions(int i, int j) {
        if (objectGroup.getChildren().get(i).getName() != null) {
            if (objectGroup.getChildren().get(i).getName().contains("player")) {
                Npc pl = (Npc) objectGroup.getChildren().get(i);
                if ((pl.playerCondition.equals(PlayerCondition.pcSitting)))
                    if (pl.getName().equals("player"))
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 1);
                    else
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 2);
            }
            super.orderExceptions(i, j);
        }
    }
}

