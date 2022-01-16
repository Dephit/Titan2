package com.mygdx.game.rooms;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Language;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Preffics;
import com.mygdx.game.model.Item;
import com.mygdx.game.model.ShopMenu;

public class ShopRoom extends BaseRoom {


    public ShopRoom() {
        super("shop");
    }

    public ShopRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "shop", player);

        player.setPlayerPostion(1650, 100);
    }

    public void setCommonButtons(){
        super.setCommonButtons();
        addButton("watchLoli","empty", "",  140, 280, 100, 200, 1f,
                () -> player.setPath(300 , 300, PlayerCondition.watchLoli));
        addButton("watchAlc","empty", "",  585, 460, 200, 250, 1f,
                () -> player.setPath(685 , 410, PlayerCondition.watchShop));
        addButton("watchAlc2","empty", "",  785, 460, 200, 250, 1f,
                () -> player.setPath(885 , 410, PlayerCondition.watchShop));
        addButton("watchAlc3","empty", "",  985, 460, 200, 250, 1f,
                () -> player.setPath(1085 , 410, PlayerCondition.watchShop));
        addButton("watchAlc3","empty", "",  1510, 360, 200, 250, 1f,
                () -> player.setPath(1610 , 310, PlayerCondition.watchShop));
        addButton("watchCam","empty", "",   244, 700, 75, 75, 1f,
                () -> player.setPath(360 , 385, PlayerCondition.watchCam));
        addButton("talkToStaffBut","empty", "",   230, 460, 75, 175, 1f,
                () -> player.setPath(340 , 420, PlayerCondition.lookinLeft));
        addButton("talkToCleanerBut","empty", "",   1250, 430, 75, 155, 1f,
                () -> player.setPath(1310 , 380, PlayerCondition.lookinUp));
        addButton("buyMenuBut","empty", "",   1100, 200, 200, 200, 1f,
                () -> player.setPath(1310 , 380,1310 , 380, PlayerCondition.stay, this::showBuyMenu));
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
            final TextButton textButton = getTextButton("pullUpOrPushUp", "refWindow", "",
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


        final TextButton closeRef = getTextButton("closeRef", "closeRef", "Close me!!",
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
        final TextButton textButton = getTextButton("menu", item.menuStyleName, item.title,
                600, 100, 812, 651, 1, ()->{}
        );

        final TextButton yes = getTextButton("description", "empty", "The potato is a starchy tuber of the plant Solanum tuberosum and is a root vegetable native to the Americas. The plant is a perennial in the nightshade family Solanaceae",
                1090, 635, 0, 0, 1f, ()->{});
        final TextButton no = getTextButton("potatoAnswer", "empty", "",
                1090, 300, 0, 0, 1f, ()->{});
        final TextButton agreePotatoButton = getTextButton("agreePotatoButton", "yesButton", getLanguage().buyText,
                650, 118, 287, 84, 1f, ()->{
                    if(!player.buyItem(item, player.refrigerator)){
                        interScreenCommunication.showToast(getLanguage().refregiratorIsFull);
                        runnable.run();
                    }
                });
        final TextButton declineFood = getTextButton("declineFood", "yesButton", getLanguage().no,
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
