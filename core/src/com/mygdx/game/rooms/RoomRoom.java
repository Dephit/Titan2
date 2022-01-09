package com.mygdx.game.rooms;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Preffics;

public class RoomRoom extends BaseRoom {


    public RoomRoom() {
        super("room");
    }

    public RoomRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "room", player);
        //Npc npc = new Npc("player2");
        //npc.clearPath();
        //npc.setPeriodicEvent();
        /*npcs.add(npc);
        objectGroup.addActor(npc);*/
        hudGroup.addActor(player.getHealthBar());
        hudGroup.addActor(player.getEnergyBar());

        player.setPlayerPostion(700, 250);
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


    public void setCommonButtons(){
        super.setCommonButtons();
        addButton("sleepButton","empty", "",  420, 355, 250, 100, 1f,
                () -> player.setPath(550 , 300, 561, 366, PlayerCondition.sleeping));
        addButton("pcSittingBut","empty",   "", 800, 120, 250, 150, 1f,
                () -> player.setPath(800 , 250, 905, 188, PlayerCondition.pcSitting));
        addButton("refButton","empty",   "",780, 580, 150, 200, 1f,
                () -> player.setPath(900 , 500, 900, 500, PlayerCondition.stay, this::showRefrigerator));

    }

    private void showRefrigerator() {
        pause = true;
        Group group = new Group();
        Runnable runnable = ()->{
            player.setPath(900 , 500, 900, 500, PlayerCondition.stay);
            pause = false;
            group.clear();
            group.remove();
        };
        final TextButton textButton = getTextButton("pullUpOrPushUp", "refWindow", "",
                1300, 50, 450, 650, 1, runnable
        );

        final TextButton potato = getTextButton("potato", "potato", "",
                1350, 555, 165, 125, 1f, this::showPotatoMenu);
        final TextButton nuggets = getTextButton("nuggets", "nuggets", "",
                1300 + 450 - 215, 555, 165, 125, 1f, this::showNuggetsMenu);
        final TextButton closeRef = getTextButton("closeRef", "closeRef", "Close me!!",
                1350,65, 350, 60, 1f, runnable);
        group.addActor(textButton);
        group.addActor(potato);
        group.addActor(closeRef);
        group.addActor(nuggets);
        buttonGroup.addActor(group);
    }

    private void showPotatoMenu() {
        pause = true;
        Group group = new Group();
        Runnable runnable = ()->{
            player.setPath(900 , 500, 900, 500, PlayerCondition.stay);
            pause = false;
            group.clear();
            group.remove();
        };
        final TextButton textButton = getTextButton("potatoMenu", "potatoMenu", "Potato",
                600, 100, 812, 651, 1, ()->{}
        );

        final TextButton yes = getTextButton("potatoMenuDescription", "empty", "The potato is a starchy tuber of the plant Solanum tuberosum and is a root vegetable native to the Americas. The plant is a perennial in the nightshade family Solanaceae",
                1090, 635, 0, 0, 1f, ()->{});
        final TextButton no = getTextButton("potatoAnswer", "empty", "",
                1090, 300, 0, 0, 1f, ()->{});
        final TextButton agreePotatoButton = getTextButton("agreePotatoButton", "yesButton", "Eat",
                650, 118, 287, 84, 1f, ()->{
                    player.eatPotato();
                    runnable.run();
                });
        final TextButton declineFood = getTextButton("declineFood", "yesButton", "Put it back",
                1060, 118, 287, 84, 1f, runnable);
        group.addActor(textButton);
        group.addActor(yes);
        group.addActor(agreePotatoButton);
        group.addActor(declineFood);
        group.addActor(no);
        buttonGroup.addActor(group);
    }

    private void showNuggetsMenu() {
        pause = true;
        Group group = new Group();
        Runnable runnable = ()->{
            player.setPath(900 , 500, 900, 500, PlayerCondition.stay);
            pause = false;
            group.clear();
            group.remove();
        };
        final TextButton textButton = getTextButton("potatoMenu", "nuggetsMenu", "",
                600, 100, 812, 651, 1, ()->{}
        );

        final TextButton yes = getTextButton("title", "empty", "Наггетсы",
                1090, 635, 0, 0, 1f, ()->{});
        final TextButton no = getTextButton("description", "empty", "блюдо американской кухни из филе куриной грудки в хрустящей панировке, обжаренной в масле. Изобретенные в 1950-х годах куриные наггетсы стали популярным блюдом ресторанов быстрого питания, а также широко продаются замороженными для домашнего использования",
                1090, 300, 0, 0, 1f, ()->{});
        final TextButton agreePotatoButton = getTextButton("eat", "yesButton", "Eat",
                650, 118, 287, 84, 1f, ()->{
                    player.eatNuggets();
                    runnable.run();
                });
        final TextButton declineFood = getTextButton("declineFood", "yesButton", "Put it back",
                1060, 118, 287, 84, 1f, runnable);
        group.addActor(textButton);
        group.addActor(yes);
        group.addActor(agreePotatoButton);
        group.addActor(declineFood);
        group.addActor(no);
        buttonGroup.addActor(group);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    protected void orderExceptions(int i, int j) {
        if (objectGroup.getChildren().get(i).getName() != null){
            if(objectGroup.getChildren().get(i).getName().contains("player")) {
                Npc pl = (Npc) objectGroup.getChildren().get(i);
                if ((pl.playerCondition.equals(PlayerCondition.pcSitting)))
                    if(pl.getName().equals("player"))
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 1);
                    else
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 2);
            }
            super.orderExceptions(i,j);
        }
    }
}

