package com.mygdx.game.rooms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Preffics;
import com.mygdx.game.StatBar;
import com.mygdx.game.Style;

public class ParkRoom extends BaseRoom {

    public ParkRoom() {
        super("park");
    }

    boolean workInProgress = false;

    public ParkRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "park", player);
        player.setPlayersAction(PlayerCondition.stay, -200, 250, ()->{});
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

    private void showWorkMenu() {
        Group group = new Group();
        //pause = true;
        Runnable runnable = () -> {
            //  pause = false;
            group.clear();
            group.remove();
            openMap();
        };
        int wokrReward = 1000;

        addButton(
                "window", Style.window, "",
                Preffics.SCREEN_WIDTH / 2 - 750, Preffics.SCREEN_HEIGHT / 2 - 250, 1500, 500, 1, group, runnable
        );
        addButton(
                "workTitle", Style.empty, getLanguage().walkInThePark,
                Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 + 100, 1000, 100, 1.8f, group, () -> {
                }
        );
        addButton(
                "workDescription", Style.empty, getLanguage().walkToRecoverYourHead,
                Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 - 25, 1000, 100, 1.8f, group, () -> {
                }
        );
        addButton(
                "work", Style.yesButton, getLanguage().doWalk,
                Preffics.SCREEN_WIDTH / 2 - 450, Preffics.SCREEN_HEIGHT / 2 - 200, 400, 125, 1.5f, group, this::showWorkProgress
        );
        addButton(
                "cancel", Style.yesButton, getLanguage().cancel,
                Preffics.SCREEN_WIDTH / 2 + 50, Preffics.SCREEN_HEIGHT / 2 - 200, 400, 125, 1.5f, group, runnable::run
        );
        buttonGroup.addActor(group);
    }

    private void showWorkProgress() {
        if (player.getEnergyBar().getCurrentAmount() > 0 && player.getHealthBar().getCurrentAmount() > 0) {
            workInProgress = true;
            Group group = new Group();
            //pause = true;
            Runnable runnable = () -> {
                //pause = false;
                workInProgress = false;
                group.clear();
                group.remove();
            };

            addButton(
                    "window", Style.window, "",
                    Preffics.SCREEN_WIDTH / 2 - 750, Preffics.SCREEN_HEIGHT / 2 - 250, 1500, 500, 1, group, runnable
            );
            addButton(
                    "workTitle", Style.empty, getLanguage().walkingInProgress,
                    Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 + 100, 1000, 100, 1.8f, group, () -> {
                    }
            );
            StatBar statBar = new StatBar("work");
            statBar.setBounds(Preffics.SCREEN_WIDTH / 2 - 400, Preffics.SCREEN_HEIGHT / 2 - 50, 800, 130);
            statBar.setProgressAndCapacity(100, 0);
            statBar.setColor(Color.valueOf("ef7b45"));
            statBar.setBackgroundColor(Color.valueOf("042a2b"));
            group.addActor(statBar);
            addButton(
                    "cancel", Style.yesButton, getLanguage().cancel,
                    Preffics.SCREEN_WIDTH / 2 - 200, Preffics.SCREEN_HEIGHT / 2 - 200, 400, 125, 1.5f, group, runnable::run
            );
            buttonGroup.addActor(group);
            new Thread(() -> {
                while (statBar.getCurrentAmount() < 100 && player.getEnergyBar().getCurrentAmount() > 0 && player.getHealthBar().getCurrentAmount() > 0 && workInProgress) {
                    statBar.addCurrentAmount(1);
                    player.onPark();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (player.getEnergyBar().getCurrentAmount() <= 0 || player.getHealthBar().getCurrentAmount() <= 0) {
                    interScreenCommunication.showToast(getLanguage().youHaveNoEnergyOrHealth);
                    runnable.run();
                    return;
                }
                if (statBar.getCurrentAmount() >= 100) {
                    player.setPlayerPosition(((int) player.getX()), (int) player.getY(), PlayerCondition.stay);
                }
                runnable.run();
            }).start();
        } else {
            interScreenCommunication.showToast(getLanguage().youHaveNoEnergyOrHealth);
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
