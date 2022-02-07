package com.mygdx.game.rooms;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Preffics;
import com.mygdx.game.Style;
import com.mygdx.game.model.CompetitionOpponent;

import java.util.ArrayList;

enum Comp{
    SQUAT_1, SQUAT_2, SQUAT_3, BENCH_1, BENCH_2, BENCH_3, DEADLIFT_1, DEADLIFT_2, DEADLIFT_3, CLOSE
}

public class CompetitionRoom extends BaseRoom {

    public CompetitionRoom() {
        super("competition");
    }

    public CompetitionRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "competition", player);
        player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215,PlayerCondition.stay);
    }

    Comp compStatus = Comp.SQUAT_1;

    public void setCommonButtons() {
        super.setCommonButtons();
        showWorkMenu();
        player.onPlayerConditionChangeListener = (oldPlayerCondition, playerCondition) -> {
            if(playerCondition == PlayerCondition.stay){
                showWorkMenu();
            }
        };
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
        if(!pause) {
            Group group = new Group();
            pause = true;
            Runnable runnable = () -> {
                pause = false;
                group.clear();
                group.remove();
            };

            addButton(
                    "window", Style.window, "",
                    Preffics.SCREEN_WIDTH / 2 - 750, Preffics.SCREEN_HEIGHT / 2 - 500, 1500, 1000, 1, group, () -> {

                    }
            );
            addButton(
                    "comtetitionTitle", Style.empty, getLanguage().comtetitionTitle,
                    Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 + 300, 1000, 100, 1.8f, group, () -> {
                    }
            );
            addButton(
                    "squatTitle", Style.empty, getSetText(),
                    Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 + 175, 1000, 100, 1.8f, group, () -> {
                    }
            );
            addButton(
                    "squatTitle", Style.empty, getResult(),
                    Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 -25, 1000, 100, 1.8f, group, () -> {
                    }
            );
            addButton(
                    "work", Style.yesButton, getLanguage().nextSet,
                    Preffics.SCREEN_WIDTH / 2 - 300, Preffics.SCREEN_HEIGHT / 2 - 200, 600, 175, 1.5f, group, () -> {
                        player.animationTime = 0;
                        getNextExercise();
                        runnable.run();
                    }
            );
            addButton(
                    "cancel", Style.yesButton, getLanguage().cancel,
                    Preffics.SCREEN_WIDTH / 2 -300, Preffics.SCREEN_HEIGHT / 2 - 400, 600, 175, 1.5f, group, interScreenCommunication::openMap
            );
            addButton(
                    "cancel", Style.yesButton, getLanguage().cancel,
                    Preffics.SCREEN_WIDTH / 2 + 400, Preffics.SCREEN_HEIGHT / 2 - 400, 600, 175, 1.5f, group, () -> {
                        showTable();
                    }
            );
            buttonGroup.addActor(group);
        }
    }

    private void showTable() {
        ArrayList list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new CompetitionOpponent(
                    "player "+ i,
                    80,
                    60,
                    80
            ));
        }
        interScreenCommunication.showPlayerList(list, object -> {
            //interScreenCommunication.openMap();
        });
    }

    private String getResult() {
        switch (compStatus){
            case SQUAT_1:
                return String.valueOf(player.getBestSquat() * 0.8f);
            case SQUAT_2:
                return String.valueOf(player.getBestSquat() * 0.9f);
            case SQUAT_3:
                return String.valueOf(player.getBestSquat() * 0.95f);
            case BENCH_1:
                return String.valueOf(player.getBestBench() * 0.8f);
            case BENCH_2:
                return String.valueOf(player.getBestBench() * 0.9f);
            case BENCH_3:
                return String.valueOf(player.getBestBench() * 0.95f);
            case DEADLIFT_1:
                return String.valueOf(player.getBestDeadlift() * 0.8f);
            case DEADLIFT_2:
                return String.valueOf(player.getBestDeadlift() * 0.9f);
            case DEADLIFT_3:
                return String.valueOf(player.getBestDeadlift() * 0.95f);
            case CLOSE:
                return getLanguage().cancel;
        }
        return "";
    }

    private String getSetText() {
        switch (compStatus){
            case SQUAT_1:
                return getLanguage().squat + " " + 1 + " " + getLanguage().set;
            case SQUAT_2:
                return getLanguage().squat + " "  + 2 + " " + getLanguage().set;
            case SQUAT_3:
                return getLanguage().squat + " "  + 3 + " " + getLanguage().set;
            case BENCH_1:
                return getLanguage().bench + " "  + 1 + " " + getLanguage().set;
            case BENCH_2:
                return getLanguage().bench + " "  + 2 + " " + getLanguage().set;
            case BENCH_3:
                return getLanguage().bench + " "  + 3 + " " + getLanguage().set;
            case DEADLIFT_1:
                return getLanguage().deadlift + " "  + 1 + " " + getLanguage().set;
            case DEADLIFT_2:
                return getLanguage().deadlift + " "  + 2 + " " + getLanguage().set;
            case DEADLIFT_3:
                return getLanguage().deadlift + " "  + 3 + " " + getLanguage().set;
            case CLOSE:
                return getLanguage().cancel;
        }
        return "";
    }

    private PlayerCondition getNextExercise() {
        float deadliftX = player.getWidth() * 1.8f;
        PlayerCondition pc = PlayerCondition.stay;
        player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 225, PlayerCondition.stay);
        switch (compStatus){
            case SQUAT_1:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compSquat);
                compStatus = Comp.SQUAT_2;
                break;
            case SQUAT_2:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compSquat);
                compStatus = Comp.SQUAT_3;
                break;
            case SQUAT_3:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compSquat);
                compStatus = Comp.BENCH_1;
                break;
            case BENCH_1:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compBench);
                compStatus = Comp.BENCH_2;
                break;
            case BENCH_2:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compBench);
                compStatus = Comp.BENCH_3;
                break;
            case BENCH_3:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compBench);
                compStatus = Comp.DEADLIFT_1;
                break;
            case DEADLIFT_1:
                player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 225, PlayerCondition.stay);
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 215, PlayerCondition.compDeadlift);
                compStatus = Comp.DEADLIFT_2;
                break;
            case DEADLIFT_2:
                player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 225, PlayerCondition.stay);
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 215, PlayerCondition.compDeadlift);
                compStatus = Comp.DEADLIFT_3;
                break;
            case DEADLIFT_3:
                player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 225, PlayerCondition.stay);
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 215, PlayerCondition.compDeadlift);
                compStatus = Comp.CLOSE;
                break;
            case CLOSE:
                interScreenCommunication.openMap();
                break;
        }
        return pc;
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
