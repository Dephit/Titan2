package com.mygdx.game.rooms;

import static com.mygdx.game.PlayerCondition.stay;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Style;
import com.mygdx.game.model.Item;

public class RoomRoom extends BaseRoom {


    public RoomRoom() {
        super("room");
    }

    public RoomRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "room", player);
        player.setPlayerPosition(700, 250);
        player.setPlayersAction(stay, 700, 250, ()->{});
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
        addButton("sleepButton", Style.empty, "",  420, 355, 250, 100, 1f,
                () -> player.setPath(550 , 300, 561, 366, PlayerCondition.sleeping));
        addButton("pcSittingBut",Style.empty,   "", 800, 120, 250, 150, 1f,
                () -> player.setPath(800 , 250, 905, 188, PlayerCondition.pcSitting));
        addButton("refButton",Style.empty,   "",780, 580, 150, 200, 1f,
                () -> player.setPath(900 , 500, 900, 500, stay, this::showRefrigerator));

    }

    Group refPopupGroup = new Group();

    private void showRefrigerator() {
        pause = true;
        Runnable runnable = ()->{
            player.setPath(900 , 500, 900, 500, stay);
            pause = false;
            refPopupGroup.clear();
            refPopupGroup.remove();
        };
        int x = 1300;
        int y = 50;
        final TextButton textButton = getTextButton("pullUpOrPushUp", Style.refWindow, "",
                x, y, 450, 650, 1, runnable
        );
        refPopupGroup.addActor(textButton);

        addToRef(x, y, runnable);

        buttonGroup.addActor(refPopupGroup);
    }

    private void addToRef(int x, int y, Runnable runnable) {
        int countX = 0;
        int countY = 0;

        for (Item item : player.refrigerator.getItems()) {
            final TextButton potato = getTextButton(item.title, item.styleName, "",
                    x + 50 + (400 - 215) * countX, y + 505 - 140 * countY, 165, 125, 1f, () ->showItemMenu(item));
            countX++;
            if (countX == 2) {
                countX = 0;
                countY++;
            }
            refPopupGroup.addActor(potato);
        }


        final TextButton closeRef = getTextButton("closeRef", Style.closeRef, "Close me!!",
                x + 50,y + 15, 350, 60, 1f, runnable);
        refPopupGroup.addActor(closeRef);
    }

    private void showItemMenu(Item item) {
        pause = true;
        Group group = new Group();
        Runnable runnable = ()->{
            player.setPath(900 , 500, 900, 500, stay);
            pause = false;
            group.clear();
            refPopupGroup.clear();
            showRefrigerator();
            group.remove();
        };
        interScreenCommunication.showDialog(
                item.title,
                item.description,
                "Eat",
                "Put it back",
                (o)-> runnable.run(),
                (o)->{
                    item.onUse(player);
                    runnable.run();
                }
        );
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

