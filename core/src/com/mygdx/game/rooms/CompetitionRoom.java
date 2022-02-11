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
import com.mygdx.game.interfaces.OnCLickCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.enums.Comp;

import java.util.ArrayList;
import java.util.Random;

public class CompetitionRoom extends BaseRoom {

    public CompetitionRoom() {
        super("competition");
    }

    public CompetitionRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "competition", player);
        player.setPlayersAction(PlayerCondition.stay, (int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, ()->{

        });
        player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.stay);
        player.compValue = new CompetitionOpponent(
                player.getName(),
                player.getBestSquat(),
                player.getBestBench(),
                player.getBestDeadlift()
        );
    }

    Comp compStatus = Comp.SQUAT_1;

    public void setCommonButtons() {
        super.setCommonButtons();
        showWorkMenu();
        //showTable();
        player.onPlayerConditionChangeListener = (oldPlayerCondition, playerCondition) -> {
            if(playerCondition == PlayerCondition.stay){
                showWorkMenu();
               // showTable();
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
            interScreenCommunication.showNextSetMenu(
                    player,
                    compStatus.attempt,
                    object -> {
                        if(object == null){
                            showTable();
                        }else {
                            switch (compStatus.attempt){
                                case 1:
                                    player.compValue.squat.firstAttempt = (CompetitionOpponent.Attempt) object;
                                    break;
                                case 2:
                                    player.compValue.squat.secondAttempt = (CompetitionOpponent.Attempt) object;
                                    break;
                                case 3:
                                    player.compValue.squat.thirdAttempt = (CompetitionOpponent.Attempt) object;
                                    break;
                                case 4:
                                    player.compValue.bench.firstAttempt = (CompetitionOpponent.Attempt) object;
                                    break;
                                case 5:
                                    player.compValue.bench.secondAttempt = (CompetitionOpponent.Attempt) object;
                                    break;
                                case 6:
                                    player.compValue.bench.thirdAttempt = (CompetitionOpponent.Attempt) object;
                                    break;
                                case 7:
                                    player.compValue.deadlift.firstAttempt = (CompetitionOpponent.Attempt) object;
                                    break;
                                case 8:
                                    player.compValue.deadlift.secondAttempt = (CompetitionOpponent.Attempt) object;
                                    break;
                                case 9:
                                    player.compValue.deadlift.thirdAttempt = (CompetitionOpponent.Attempt) object;
                                    break;
                            }
                            player.animationTime = 0;
                            getNextExercise();
                            runnable.run();
                        }
                    }

            );

            /*addButton(
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
            );*/
            buttonGroup.addActor(group);
        }
    }

    ArrayList opponentList;

    void createOpponents(){
        if(opponentList == null) {
            int avgLvl = (player.getSquatLvl() + player.getBenchLvl() + player.getDlLvl()) / 3;
            avgLvl = avgLvl > 0 ? avgLvl : 1;
            opponentList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                opponentList.add(new CompetitionOpponent(
                        "player " + i,
                        avgLvl
                ));
            }
            opponentList.add(player.compValue);
        }
    }


    private void showTable() {
        createOpponents();
        interScreenCommunication.showPlayerList(opponentList, compStatus, object -> {
            pause = false;
            showWorkMenu();
        });
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
                compStatus.attempt = 2;
                break;
            case SQUAT_2:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compSquat);
                compStatus = Comp.SQUAT_3;
                compStatus.attempt = 3;
                break;
            case SQUAT_3:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compSquat);
                compStatus = Comp.BENCH_1;
                compStatus.attempt = 4;
                break;
            case BENCH_1:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compBench);
                compStatus = Comp.BENCH_2;
                compStatus.attempt = 5;
                break;
            case BENCH_2:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compBench);
                compStatus = Comp.BENCH_3;
                compStatus.attempt = 6;
                break;
            case BENCH_3:
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, PlayerCondition.compBench);
                compStatus = Comp.DEADLIFT_1;
                compStatus.attempt = 7;
                break;
            case DEADLIFT_1:
                player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 225, PlayerCondition.stay);
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 215, PlayerCondition.compDeadlift);
                compStatus = Comp.DEADLIFT_2;
                compStatus.attempt = 8;
                break;
            case DEADLIFT_2:
                player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 225, PlayerCondition.stay);
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 215, PlayerCondition.compDeadlift);
                compStatus = Comp.DEADLIFT_3;
                compStatus.attempt = 9;
                break;
            case DEADLIFT_3:
                player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 225, PlayerCondition.stay);
                player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - deadliftX), 215, PlayerCondition.compDeadlift);
                compStatus = Comp.CLOSE;
                compStatus.attempt = 10 ;
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
