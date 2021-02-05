package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

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
                if (mapArr[(int) touchPos.y / preffics.mapCoordinateCorrector][(int) touchPos.x / preffics.mapCoordinateCorrector] == -1) {
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
                        case "dumbels":
                            player.setDumbellExercise();
                            break;
                        case "pullups":
                            player.setPullUps();
                            break;
                        case "legpress":
                            player.setLegPress();
                            break;
                    }
                }

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };
    }

    @Override
    protected void orderExceptions(int i, int j) {
        if (getActors().get(i).getName() != null)
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
    }
}


