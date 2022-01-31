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

import org.apache.commons.logging.Log;

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
                    Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 + 100, 1000, 100, 1.8f, group, () -> {
                    }
            );
            addButton(
                    "squatTitle", Style.empty, getLanguage().squatTitle,
                    Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 - 25, 1000, 100, 1.8f, group, () -> {
                    }
            );
            addButton(
                    "work", Style.yesButton, getLanguage().nextSet,
                    Preffics.SCREEN_WIDTH / 2 - 450, Preffics.SCREEN_HEIGHT / 2 - 200, 400, 125, 1.5f, group, () -> {
                        player.setPlayerPosition((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 225, PlayerCondition.stay);
                        player.animationTime = 0;
                        player.setPath((int) (Preffics.SCREEN_WIDTH / 2 - player.getWidth() / 2f), 215, getNextExerCise());
                        runnable.run();
                    }
            );
            addButton(
                    "cancel", Style.yesButton, getLanguage().cancel,
                    Preffics.SCREEN_WIDTH / 2 + 50, Preffics.SCREEN_HEIGHT / 2 - 200, 400, 125, 1.5f, group, () -> {
                        interScreenCommunication.openMap();
                    }
            );
            buttonGroup.addActor(group);
        }
    }

    private PlayerCondition getNextExerCise() {
        PlayerCondition pc = PlayerCondition.stay;
        switch (compStatus){
            case SQUAT_1:
                pc = PlayerCondition.compSquat;
                compStatus = Comp.SQUAT_2;
                break;
            case SQUAT_2:
                pc = PlayerCondition.compSquat;
                compStatus = Comp.SQUAT_3;
                break;
            case SQUAT_3:
                pc = PlayerCondition.compSquat;
                compStatus = Comp.BENCH_1;
                break;
            case BENCH_1:
                pc = PlayerCondition.compBench;
                compStatus = Comp.BENCH_2;
                break;
            case BENCH_2:
                pc = PlayerCondition.compBench;
                compStatus = Comp.BENCH_3;
                break;
            case BENCH_3:
                pc = PlayerCondition.compBench;
                compStatus = Comp.DEADLIFT_1;
                break;
            case DEADLIFT_1:
                pc = PlayerCondition.compDeadlift;
                compStatus = Comp.DEADLIFT_2;
                break;
            case DEADLIFT_2:
                pc = PlayerCondition.compDeadlift;
                compStatus = Comp.DEADLIFT_3;
                break;
            case DEADLIFT_3:
                pc = PlayerCondition.compDeadlift;
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
