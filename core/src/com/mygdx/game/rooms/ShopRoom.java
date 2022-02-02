package com.mygdx.game.rooms;

import static com.mygdx.game.PlayerCondition.lookinLeft;
import static com.mygdx.game.PlayerCondition.stay;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Message;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Preffics;
import com.mygdx.game.Style;
import com.mygdx.game.model.Item;
import com.mygdx.game.model.ShopMenu;

import java.util.ArrayList;

public class ShopRoom extends BaseRoom {


    public ShopRoom() {
        super("shop");
    }

    public ShopRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "shop", player);
        player.setPlayerPosition(1650, 100);
        player.setPlayersAction(stay,1650, 100, ()->{

        });
    }

    public void setCommonButtons(){
        super.setCommonButtons();
        addButton("watchLoli", Style.empty, "",  140, 280, 100, 200, 1f,
                () -> player.setPath(300 , 300, PlayerCondition.watchLoli));
        addButton("watchAlc", Style.empty, "",  585, 460, 200, 250, 1f,
                () -> player.setPath(685 , 410, PlayerCondition.watchShop));
        addButton("watchAlc2", Style.empty, "",  785, 460, 200, 250, 1f,
                () -> player.setPath(885 , 410, PlayerCondition.watchShop));
        addButton("watchAlc3", Style.empty, "",  985, 460, 200, 250, 1f,
                () -> player.setPath(1085 , 410, PlayerCondition.watchShop));
        addButton("watchAlc3", Style.empty, "",  1510, 360, 200, 250, 1f,
                () -> player.setPath(1610 , 310, PlayerCondition.watchShop));
        addButton("watchCam", Style.empty, "",   244, 700, 75, 75, 1f,
                () -> player.setPath(360 , 385, PlayerCondition.watchCam));
        addButton("talkToStaffBut", Style.empty, "",   230, 460, 75, 175, 1f, this::talkToStaff);
        addButton("talkToCleanerBut", Style.empty, "",   1250, 430, 75, 155, 1f,
                this::talkToSanitar);
        addButton("buyMenuBut", Style.empty, "",   1100, 200, 200, 200, 1f,
                () -> player.setPath(1310 , 380,1310 , 380, PlayerCondition.stay, this::showBuyMenu));
    }

    private void talkToSanitar() {
        /*player.setPath(1310 , 380, 0, 0, PlayerCondition.lookinUp, () -> {
            Message message = new Message(810, 500, false,
                    new ArrayList<>(),
                    getLanguage().sanitarRandomText
            );
            if (!hudGroup.getChildren().contains(message, false))
                hudGroup.addActor(message);
        });*/
    }

    private void talkToStaff() {
        player.setPath(340 , 420, 0, 0, PlayerCondition.lookinLeft, () -> {
            Message message = new Message(260, 630, true,
                    new ArrayList<>(),
                    getLanguage().stuffRandomText
            );
            if (!hudGroup.getChildren().contains(message, false))
                hudGroup.addActor(message);
        });
    }

    ShopMenu shoMenu = new ShopMenu();

    private void showBuyMenu() {
            Group refPopupGroup = new Group();
            pause = true;
            Runnable runnable = ()->{
                player.setPath(900 , 500, 900, 500, PlayerCondition.stay);
                pause = false;
                refPopupGroup.clear();
                refPopupGroup.remove();
            };
            int x = 1300;
            int y = 50;
            final TextButton textButton = getTextButton("pullUpOrPushUp",  Style.refWindow, "",
                    x, y, 450, 650, 1, runnable
            );
            refPopupGroup.addActor(textButton);
        int countX = 0;
        int countY = 0;

        for (Item item : shoMenu.getItems()) {
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
            buttonGroup.addActor(refPopupGroup);
        }


    private void showItemMenu(Item item) {
        pause = true;
        Group group = new Group();
        Runnable runnable = ()->{
            player.setPath(900 , 500, 900, 500, PlayerCondition.stay);
            pause = false;
            group.clear();
            group.remove();
        };
        final TextButton textButton = getTextButton("menu", item.menuStyleName, "",
                600, 100, 812, 651, 1, ()->{}
        );

        final TextButton yes = getTextButton("description",  Style.empty, item.description,
                1090, 470, 0, 0, 1.5f, ()->{});
        final TextButton no = getTextButton("potatoAnswer",  Style.empty, "",
                1090, 300, 0, 0, 1.5f, ()->{});
        final TextButton agreePotatoButton = getTextButton("agreePotatoButton", Style.yesButton, getLanguage().buyText,
                650, 118, 287, 84, 1.5f, ()->{
                    if(!player.buyItem(item, player.refrigerator)){
                        interScreenCommunication.showToast(getLanguage().refregiratorIsFull);
                        runnable.run();
                    }
                });
        final TextButton declineFood = getTextButton("declineFood", Style.yesButton, getLanguage().no,
                1060, 118, 287, 84, 1f, runnable);
        group.addActor(textButton);
        group.addActor(yes);
        group.addActor(agreePotatoButton);
        group.addActor(declineFood);
        group.addActor(no);
        buttonGroup.addActor(group);
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

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    protected void orderExceptions(int i, int j) {
        if (objectGroup.getChildren().get(i).getName() != null){
            if(objectGroup.getChildren().get(i).getName().contains("player")) {
                Npc pl = (Npc) objectGroup.getChildren().get(i);
                /*if ((pl.playerCondition.equals(PlayerCondition.watchLoli)))
                    if(pl.getName().equals("player"))
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 1);
                    else
                        objectGroup.getChildren().swap(i, objectGroup.getChildren().size - 2);*/
            }
            super.orderExceptions(i,j);
        }
    }
}
