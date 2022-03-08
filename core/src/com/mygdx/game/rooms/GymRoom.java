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
import static com.mygdx.game.PlayerCondition.stay;

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
import com.mygdx.game.Style;
import com.mygdx.game.interfaces.OnCLickCallback;

import java.util.ArrayList;
import java.util.Map;

public class GymRoom extends BaseRoom {

    public GymRoom() {
        super("gym");
    }

    Group exrGroup = new Group();

    public GymRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "gym", player);
        Npc npc = new Npc("player2");
        npc.clearPath();
        npc.setPeriodicEvent(player);
        npcs.add(npc);
        objectGroup.addActor(npc);
        player.setPlayerPosition(400,100);
        player.setPlayersAction(stay, 400, 100, ()->{});
        hudGroup.addActor(exrGroup);
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

    private void setTalkingToAGirl(Preffics preffics) {
        player.setPath(1025, 450, 0, 0, lookinUp, () -> {
            Message message = new Message("Girl",
                    preffics.getLanguage().armGirlDialogTree,
                    preffics.getLanguage().armGirlRandomText
            );
            showDialog(message);
            player.setPlayersAction(stay, 1025, 450, ()->{});
        });
    }

    private void setTalkingToBicepsGuy(Preffics preffics) {
            player.setPath(400, 300, 0, 0, lookinLeft, () -> {
                Message message = new Message(
                        "Guy",
                        new ArrayList<>(),
                        preffics.getLanguage().dumbelGuyRandomText
                );
                showDialog(message);
                player.setPlayersAction(stay, 400, 300, ()->{});
            });
    }

    private void setTalkingCoach(Preffics preffics) {
        player.setPath(350, 50, 0, 0, lookinLeft, () -> {
            Message message = new Message("Coach",
                    preffics.getLanguage().coachDialogTree,
                    preffics.getLanguage().coachRandomText
            );
            showDialog(message);
            player.setPlayersAction(stay, 350, 50, ()->{});
        });
    }

    @Override
    public void draw() {
        super.draw();
        Exercise bar = player.isInExercise();
        if(bar != null && !bar.statBar.hasParent()){
            exrGroup.clear();
            exrGroup.addActor(bar.statBar);
        }
        if(bar == null && exrGroup.hasChildren()){
            exrGroup.clear();
        }
    }

    @Override
    protected void orderExceptions(int i, int j) {
        Npc player = objectGroup.findActor("player");
        Npc npc = objectGroup.findActor("player2");

        if(player.doesOverDrawExercise() && npc.doesOverDrawExercise()) {
            objectGroup.swapActor(player, objectGroup.getChildren().get(objectGroup.getChildren().size - 1));
            objectGroup.swapActor(npc, objectGroup.getChildren().get(objectGroup.getChildren().size - 2));
        }else if(player.doesOverDrawExercise()){
            objectGroup.swapActor(player, objectGroup.getChildren().get(objectGroup.getChildren().size - 1));
        }else if(npc.doesOverDrawExercise()){
            objectGroup.swapActor(npc, objectGroup.getChildren().get(objectGroup.getChildren().size - 1));
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
        final TextButton textButton = getTextButton("pullUpOrPushUp", Style.window, "",
                Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 - 250, 1000, 500, 1, runnable
        );
        final TextButton text = getTextButton("text", Style.empty, "Какое упражнение хотите сделать?",
                Preffics.SCREEN_WIDTH / 2 - 500, Preffics.SCREEN_HEIGHT / 2 + 100, 1000, 100, 1.8f,  ()->{});

        final TextButton yes = getTextButton("pullUpButton", Style.yesButton, "Подтягивания",
                Preffics.SCREEN_WIDTH / 2 - 450, Preffics.SCREEN_HEIGHT / 2 - 200, 400, 125, 1.5f, ()-> {
                    setExercise(()->player.setPullUps(), pullUps);
                    runnable.run();
        });
        final TextButton no = getTextButton("pushUpButton", Style.yesButton, "Брусья",
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
}


