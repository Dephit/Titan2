package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mygdx.game.PlayerCondition.lookinLeft;
import static com.mygdx.game.PlayerCondition.lookinUp;

public class GymRoom extends BaseRoom{

    public GymRoom() {
        super("gym");
    }

    public GymRoom(Player player) {
        super("gym", player);
        Npc npc = new Npc("player2");
        npc.clearPath();
        npc.setPeriodicEvent();
        npcs.add(npc);
        addActor(npc);
    }

    @Override
    protected InputListener getEventListener(String text, Runnable onFinish) {
        return  new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Preffics preffics = Preffics.getInstance();
                double[][] mapArr = preffics.mapArr;
                int touchY = (int) touchPos.y / preffics.mapCoordinateCorrector;
                int touchX = (int) touchPos.x / preffics.mapCoordinateCorrector;
                try{
                    if (mapArr[touchY][touchX] == -1) {
                        switch (text) {
                            case "squat":
                                player.setSquatExercise();
                                break;
                            case "deadlift":
                                player.setDeadliftExercise();
                                break;
                            case "bench":
                                player.setBenchExercise();
                                break;
                            case "hiper":
                                player.setHyperExercise();
                                break;
                            case "benchNearCoach":
                                player.set1BenchSitting();
                                break;
                            case "benchNearTreads":
                                player.set2BenchSitting();
                                break;
                            case "benchesNearDumbs": {
                                if(x < 235) {
                                    setTalkingToBicepsGuy(preffics);
                                }
                                break;
                            }
                            case "armwrestlers": {
                                setTalkingToAGirl(preffics);
                                break;
                            }
                            case "coach": {
                                setTalkingCoach(preffics);
                                break;
                            }
                            case "pullups":
                                player.setPullUps();
                                break;
                            case "legpress":
                                player.setLegPress();
                                break;
                        }
                    }
                }catch (Exception e){

                }

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };
    }

    private void setTalkingToAGirl(Preffics preffics) {
        player.setPath(1025, 450, 0, 0, lookinUp, () -> {
            Message message = new Message(1070, 670, true,
                    preffics.getLanguage().armGirlDialogTree,
                    preffics.getLanguage().armGirlRandomText
            );
            if (!getActors().contains(message, false))
                addActor(message);
        });
    }

    private void setTalkingToBicepsGuy(Preffics preffics) {
            player.setPath(400, 300, 0, 0, lookinLeft, () -> {
                Message message = new Message(300, 500, true,
                        new ArrayList<>(),
                        preffics.getLanguage().dumbelGuyRandomText
                );
                if (!getActors().contains(message, false))
                    addActor(message);
            });
    }

    private void setTalkingCoach(Preffics preffics) {
        player.setPath(350, 50, 0, 0, lookinLeft, () -> {
            Message message = new Message(100, 240, true,
                    preffics.getLanguage().coachDialogTree,
                    preffics.getLanguage().coachRandomText
            );
            if (!getActors().contains(message, false))
                addActor(message);
        });
    }

    @Override
    protected void orderExceptions(int i, int j) {
        if (getActors().get(i).getName() != null){
            if(getActors().get(i).getName().contains("player")) {
                Npc pl = (Npc) getActors().get(i);
                if ((pl.playerCondition.equals(PlayerCondition.bench) ||
                        pl.playerCondition.equals(PlayerCondition.pullUps) ||
                        pl.playerCondition.equals(PlayerCondition.legPress) ||
                        pl.playerCondition.equals(PlayerCondition.sitting) ||
                        pl.playerCondition.equals(PlayerCondition.sittingRev) ||
                        pl.playerCondition.equals(PlayerCondition.hiper) ||
                        pl.playerCondition.equals(PlayerCondition.pushUps))||
                        pl.playerCondition.equals(PlayerCondition.pcSitting))
                    if(pl.getName().equals("player"))
                        getActors().swap(i, getActors().size - 1);
                    else
                        getActors().swap(i, getActors().size - 2);
            }
            super.orderExceptions(i,j);
        }
    }
}


