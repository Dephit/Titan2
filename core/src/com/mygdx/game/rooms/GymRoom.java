package com.mygdx.game.rooms;

import static com.mygdx.game.PlayerCondition.bench;
import static com.mygdx.game.PlayerCondition.deadlift;
import static com.mygdx.game.PlayerCondition.hiper;
import static com.mygdx.game.PlayerCondition.legPress;
import static com.mygdx.game.PlayerCondition.lookinLeft;
import static com.mygdx.game.PlayerCondition.lookinUp;
import static com.mygdx.game.PlayerCondition.pullUps;
import static com.mygdx.game.PlayerCondition.pushUps;
import static com.mygdx.game.PlayerCondition.sitting;
import static com.mygdx.game.PlayerCondition.sittingRev;
import static com.mygdx.game.PlayerCondition.squat;

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

public class GymRoom extends BaseRoom {

    public GymRoom() {
        super("gym");
    }

    public GymRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "gym", player);
        Npc npc = new Npc("player2");
        npc.clearPath();
        npc.setPeriodicEvent(player);
        npcs.add(npc);
        objectGroup.addActor(npc);
        player.setPlayerPostion(400,100);
    }

    @Override
    public void dispose() {
        for(Npc npc: npcs){
            npc.isActive = false;
        }
        super.dispose();
    }

    @Override
    protected InputListener getEventListener(String text, Runnable onFinish) {
        return  new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Preffics preffics = Preffics.getInstance();
                try{
                    switch (text) {
                        case "squat":
                            if(x < 410) {
                                setExercise(()->setUpSquat(), squat);
                            }
                            break;
                        case "deadlift":
                            setExercise(()->setDeadlift(), deadlift);
                            break;
                        case "bench":
                            setExercise(()->setBench(), bench);
                            break;
                        case "hiper":
                            setExercise(player::setHyperExercise, hiper);
                            break;
                        case "benchNearCoach":
                            setExercise(player::set1BenchSitting, sitting);
                            break;
                        case "benchNearTreads":
                            setExercise(player::set2BenchSitting, sittingRev);
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
                            addPullPushChooser();
                            break;
                        case "legpress":
                            setExercise(player::setLegPress, legPress);
                            break;
                        default:
                            onFinish.run();
                            break;
                    }
                }catch (Exception ignored){

                }

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };
    }

    private void setExercise(Runnable runnable, PlayerCondition playerCondition) {
        if(!someoneDoingIt(playerCondition)) {
            runnable.run();
        }else{
            interScreenCommunication.showToast(getLanguage().thisIsTaken);
        }
    }

    private void setBench() {
        player.setBenchExercise();
    }

    private void setDeadlift() {
        player.setDeadliftExercise();
    }

    private void setUpSquat() {
        if(!someoneDoingIt(PlayerCondition.squat)) {
            player.setModerateSquat();
        }else{
            interScreenCommunication.showToast(getLanguage().thisIsTaken);
        }
    }

    private boolean someoneDoingIt(PlayerCondition squat) {
        boolean isDoing = false;
        for (Npc npc : npcs){
            if (npc.playerCondition == squat) {
                isDoing = true;
                break;
            }
        }
        return isDoing;
    }

    private void setPushPullUps(Preffics preffics) {
        player.setPath(800, 430, 820, 440, lookinUp, this::addPullPushChooser);
    }

    private void setTalkingToAGirl(Preffics preffics) {
        player.setPath(1025, 450, 0, 0, lookinUp, () -> {
            Message message = new Message(1070, 670, true,
                    preffics.getLanguage().armGirlDialogTree,
                    preffics.getLanguage().armGirlRandomText,
                    player.currentGirlDialogProgress,
                    () -> player.currentGirlDialogProgress++
            );
            if (!hudGroup.getChildren().contains(message, false))
                hudGroup.addActor(message);
        });
    }

    private void setTalkingToBicepsGuy(Preffics preffics) {
            player.setPath(400, 300, 0, 0, lookinLeft, () -> {
                Message message = new Message(300, 500, true,
                        new ArrayList<>(),
                        preffics.getLanguage().dumbelGuyRandomText
                );
                if (!hudGroup.getChildren().contains(message, false))
                    hudGroup.addActor(message);
            });
    }

    private void setTalkingCoach(Preffics preffics) {
        player.setPath(350, 50, 0, 0, lookinLeft, () -> {
            Message message = new Message(100, 240, true,
                    preffics.getLanguage().coachDialogTree,
                    preffics.getLanguage().coachRandomText,
                    player.coachDialogProgress,
                    () -> player.coachDialogProgress++
            );
            if (!hudGroup.getChildren().contains(message, false))
                hudGroup.addActor(message);
        });
    }

    @Override
    public void draw() {
        super.draw();
        Exercise bar = player.isInExercise();
        if(bar != null && !bar.statBar.hasParent()){
            hudGroup.addActor(bar.statBar);
        }//else player.clearExrercise();
    }

    @Override
    protected void orderExceptions(int i, int j) {
        if (objectGroup.getChildren().get(i).getName() != null){
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
        }
    }

    private void addPullPushChooser() {
        pause = true;
        Group group = new Group();
        Runnable runnable = ()->{
            pause = false;
            group.clear();
            group.remove();
        };
        final TextButton textButton = getTextButton("pullUpOrPushUp", "window", "",
                Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 - 250, 1000, 500, 1, runnable
        );
        final TextButton text = getTextButton("text", "empty", "Какое упражнение хотите сделать?",
                Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 + 100, 1000, 100, 1.8f,  ()->{});

        final TextButton yes = getTextButton("pullUpButton", "yesButton", "Подтягивания",
                Preffics.SCREEN_WIDTH / 2 - 450, Preffics.SCREEN_HEIGHT / 2 - 200, 400, 125, 1.5f, ()-> {
                    setExercise(()->player.setPullUps(), pullUps);
                    runnable.run();
        });
        final TextButton no = getTextButton("pushUpButton", "yesButton", "Брусья",
                Preffics.SCREEN_WIDTH / 2 + 50, Preffics.SCREEN_HEIGHT / 2 - 200, 400, 125, 1.5f, ()-> {
                    setExercise(()->player.setPushUps(), pushUps);
                    runnable.run();
        });
        group.addActor(textButton);
        group.addActor(yes);
        group.addActor(text);
        group.addActor(no);
        buttonGroup.addActor(group);
    }

    private void addExerciseChoseMenuPushChooser(String title, Map<String, Runnable> map) {
        pause = true;
        Group group = new Group();
        Runnable runnable = ()->{
            pause = false;
            group.clear();
            group.remove();
        };
        final TextButton textButton = getTextButton("pullUpOrPushUp", "window", "",
                Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 - 150 - 150 * map.size() / 4, 1000, 300 + 150 *  map.size() / 2, 1, ()->{}
        );
        final TextButton text = getTextButton("text", "empty", title,
                Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 + 100, 1000, 100, 1.8f,  ()->{});

        final TextButton bg = getTextButton("bg", "empty", "",
                0, 0, Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT, 1.5f, runnable);

        group.addActor(bg);
        group.addActor(textButton);
        group.addActor(text);

        int i = 1;
        int g = 0;
        for (String key: map.keySet()){
            int x = (i) % 2 == 0 ? Preffics.SCREEN_WIDTH / 2 - 450 :  Preffics.SCREEN_WIDTH / 2 + 50;
            int y = Preffics.SCREEN_HEIGHT / 2 - 100 - 150 * g;
            final TextButton heavy = getTextButton("pullUpButton", "yesButton", key,
                    x, y, 400, 125, 1.5f, ()->{
                        map.get(key).run();
                        runnable.run();
                    });
            i++;
            if((i) % 2 != 0){
                g++;
            }
            group.addActor(heavy);
        }

        buttonGroup.addActor(group);
    }
}


