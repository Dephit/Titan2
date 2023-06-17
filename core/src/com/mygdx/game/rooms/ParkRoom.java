package com.mygdx.game.rooms;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.interfaces.OnClickCallback;

//TODO make this room
public class ParkRoom extends BaseRoom {

    public ParkRoom() {
        super("park");
    }

    boolean workInProgress = false;

    public ParkRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "park", player);
        player.setPlayersAction(PlayerCondition.stay, -200, 250, () -> {
        });
    }


    public void setCommonButtons() {
        super.setCommonButtons();
        showWorkMenu();
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

    @Override
    public void onClose() {
        openMap();
    }

    private void showWorkMenu() {
        interScreenCommunication.showDialog(
            getLanguage().walkInThePark,
            getLanguage().walkToRecoverYourHead,
            getLanguage().doWalk,
            getLanguage().cancel,
            (o) -> {
                callOnClose = true;
                //onClose();
            },
            (o) -> showWorkProgress()

        );
    }

    private void showWorkProgress() {
        if (player.exerciseManager.energy.getCurrentAmount() > 0 && player.exerciseManager.health.getCurrentAmount() > 0) {
            workInProgress = true;
            //pause = true;
            Runnable runnable = () -> {
                //pause = false;
                workInProgress = false;
                showWorkMenu();
            };

            interScreenCommunication.showProgressBar(
                getLanguage().walkingInProgress,
                //onEnd
                (OnClickCallback) (o) -> {
                    player.setPlayerPosition(((int) player.getX()), (int) player.getY(), PlayerCondition.stay);
                    runnable.run();
                },
                //onUpdate
                () -> {
                    if (player.exerciseManager.energy.getCurrentAmount() <= 0 || player.exerciseManager.health.getCurrentAmount() <= 0) {
                        player.postNotificationMessage(getLanguage().youHaveNoEnergyOrHealth);
                        runnable.run();
                        return false;
                    }
                    player.exerciseManager.onPark();
                    return player.exerciseManager.energy.getCurrentAmount() > 0 && player.exerciseManager.health.getCurrentAmount() > 0 && workInProgress;
                },
                //onClose
                (o) -> runnable.run()
            );
        } else {
            player.postNotificationMessage(getLanguage().youHaveNoEnergyOrHealth);
            openMap();
        }
    }

    private void openMap() {
        workInProgress = false;
        interScreenCommunication.openMap();
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
                if ((pl.playerCondition.equals(PlayerCondition.bench) ||
                    pl.playerCondition.equals(PlayerCondition.pullUps) ||
                    pl.playerCondition.equals(PlayerCondition.legPress) ||
                    pl.playerCondition.equals(PlayerCondition.sitting) ||
                    pl.playerCondition.equals(PlayerCondition.sittingRev) ||
                    pl.playerCondition.equals(PlayerCondition.hiper) ||
                    pl.playerCondition.equals(PlayerCondition.pushUps)) ||
                    pl.playerCondition.equals(PlayerCondition.pcSitting))
                    if (pl.getName().equals("player"))
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 1);
                    else
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 2);
            }
            super.orderExceptions(i, j);
        }
    }
}
