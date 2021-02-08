package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.PlayerCondition.lookinLeft;
import static com.mygdx.game.PlayerCondition.lookinUp;

public class GymRoom extends BaseRoom{

    public GymRoom() {
        super("gym");
    }

    public GymRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "gym", player);
        Npc npc = new Npc("player2");
        npc.clearPath();
        npc.setPeriodicEvent();
        npcs.add(npc);
        objectGroup.addActor(npc);
        hudGroup.addActor(player.getSquatBar());
        hudGroup.addActor(player.getHealthBar());
        hudGroup.addActor(player.getEnergyBar());
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
                                setUpSquat(preffics);
                            }
                            break;
                        case "deadlift":
                            setDeadlift(preffics);
                            break;
                        case "bench":
                            setBench(preffics);
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
                            addPullPushChooser();
                            break;
                        case "legpress":
                            player.setLegPress();
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

    private void setBench(Preffics preffics) {
        HashMap<String, Runnable> map = new HashMap<>();
        map.put(preffics.getLanguage().heavyBench, ()-> player.setBenchExercise());
        map.put(preffics.getLanguage().moderateBench, ()-> player.setBenchExercise());
        map.put(preffics.getLanguage().easyBench, ()-> player.setBenchExercise());
        map.put(preffics.getLanguage().mockBench, ()-> player.setBenchExercise());
        addExerciseChoseMenuPushChooser(preffics.getLanguage().chooseYourExercise, map);
    }

    private void setDeadlift(Preffics preffics) {
        HashMap<String, Runnable> map = new HashMap<>();
        map.put(preffics.getLanguage().heavyDeadlift, ()-> player.setDeadliftExercise());
        map.put(preffics.getLanguage().moderateDeadlift, ()-> player.setDeadliftExercise());
        map.put(preffics.getLanguage().easyDeadlift, ()-> player.setDeadliftExercise());
        map.put(preffics.getLanguage().mockDeadlift, ()-> player.setDeadliftExercise());
        addExerciseChoseMenuPushChooser(preffics.getLanguage().chooseYourExercise, map);
    }

    private void setUpSquat(Preffics preffics) {
        HashMap<String, Runnable> map = new HashMap<>();
        map.put(preffics.getLanguage().heavySquat, ()-> player.setHeavySquat());
        map.put(preffics.getLanguage().moderateSquat, ()-> player.setModerateSquat());
        map.put(preffics.getLanguage().easySquat, ()-> player.setEasySquat());
        map.put(preffics.getLanguage().mockSquat, ()-> player.setMockSquat());
        addExerciseChoseMenuPushChooser(preffics.getLanguage().chooseYourExercise, map);
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
            player.setPullUps();
            runnable.run();
        });
        final TextButton no = getTextButton("pushUpButton", "yesButton", "Брусья",
                Preffics.SCREEN_WIDTH / 2 + 50, Preffics.SCREEN_HEIGHT / 2 - 200, 400, 125, 1.5f, ()-> {
            player.setPushUps();
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


