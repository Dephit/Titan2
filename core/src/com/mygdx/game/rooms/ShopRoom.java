package com.mygdx.game.rooms;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;

public class ShopRoom extends BaseRoom {


    public ShopRoom() {
        super("shop");
    }

    public ShopRoom(InterScreenCommunication _communication, Player player) {
        super(_communication, "shop", player);
        hudGroup.addActor(player.getHealthBar());
        hudGroup.addActor(player.getEnergyBar());

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
