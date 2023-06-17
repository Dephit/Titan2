package com.mygdx.game.rooms;

import static com.mygdx.game.PlayerCondition.stay;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.BaseRoom;
import com.mygdx.game.InterScreenCommunication;
import com.mygdx.game.Message;
import com.mygdx.game.Npc;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Style;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.enums.InventoryType;
import com.mygdx.game.model.items.OnItemClick;
import com.mygdx.game.model.shop.EquipmentShopMenu;
import com.mygdx.game.model.shop.ShopMenu;
import com.mygdx.game.model.shop.SnackMenu;

import java.util.ArrayList;

public class ShopRoom extends BaseRoom {

    Container shopMenu = new ShopMenu();
    Container snackMenu = new SnackMenu();
    Container equipmentShopMenu = new EquipmentShopMenu();

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
                () -> player.goToDestination(300 , 300, PlayerCondition.watchLoli));
        addButton("watchAlc", Style.empty, "",  585, 460, 200, 250, 1f,
                () -> player.goToDestination(685 , 410, PlayerCondition.watchShop));
        addButton("watchAlc2", Style.empty, "",  785, 460, 200, 250, 1f,
                () -> player.goToDestination(885 , 410, PlayerCondition.watchShop));
        addButton("watchAlc3", Style.empty, "",  985, 460, 200, 250, 1f,
                () -> player.goToDestination(1085 , 410, PlayerCondition.watchShop));
        addButton("watchAlc3", Style.empty, "",  1510, 360, 200, 250, 1f,
                () -> player.goToDestination(1610 , 310,1610 , 310, PlayerCondition.stay, this::showBuySnackMenu)
        );
        addButton("watchCam", Style.empty, "",   244, 700, 75, 75, 1f,
                () -> player.goToDestination(360 , 385, PlayerCondition.watchCam));
        addButton("talkToStaffBut", Style.empty, "",   230, 460, 75, 175, 1f,
                () -> player.goToDestination(340 , 420,340 , 420, PlayerCondition.stay, this::talkToStaff));
        addButton("talkToCleanerBut", Style.empty, "",   1250, 430, 75, 155, 1f, this::talkToSanitaire);
        addButton("buyMenuBut", Style.empty, "",   1100, 200, 200, 200, 1f,
                () -> player.goToDestination(1310 , 380,1310 , 380, PlayerCondition.stay, this::showBuyMenu));
    }

    private void talkToSanitaire() {
        player.goToDestination(1310 , 380, 0, 0, PlayerCondition.lookinUp, () -> {
            Message message = new Message("Sanitar",
                    new ArrayList<>(),
                    getLanguage().sanitarRandomText
            );
            showDialog(message);
            player.setPlayersAction(stay, 1310 , 380, ()->{});
        });
    }

    private void talkToStaff() {
        Runnable pauseRunnable = pauseGame(
                () -> player.goToDestination(340 , 420, PlayerCondition.stay)
        );


        OnItemClick onBuyRunnable = object -> {
            player.buyEquipmentItem((ContiniousItem) object);
            talkToStaff();
        };

        showBuyMenu(player.inventoryManager.equipmentContainer, equipmentShopMenu, onBuyRunnable, pauseRunnable);
    }

    void showBuyMenu(Container inventoryContainer, Container shopContainer, OnItemClick onBuyRunnable, Runnable onCancel) {
        interScreenCommunication.showBuyMenu(InventoryType.EQUIPMENT, inventoryContainer, shopContainer, onBuyRunnable, onCancel);
    }

    private void showBuySnackMenu() {
        Runnable pauseRunnable = pauseGame(
                () -> player.goToDestination(1610 , 310, PlayerCondition.stay)
        );


        OnItemClick onBuyRunnable = object -> {
            player.buyItemToInventory(object);
            showBuySnackMenu();
        };

        interScreenCommunication.showBuyMenu(InventoryType.INVENTORY, player.inventoryManager.inventory, snackMenu, onBuyRunnable, pauseRunnable);
    }

    private void showBuyMenu() {
        Runnable pauseRunnable = pauseGame(
                () -> player.goToDestination(900 , 500, 900, 500, PlayerCondition.stay)
        );

        OnItemClick onBuyRunnable = object -> {
            player.buyItemToRefrigerator(object);
            showBuyMenu();
        };

        interScreenCommunication.showBuyMenu(InventoryType.REFRIGERATOR, player.inventoryManager.refrigerator, shopMenu, onBuyRunnable, pauseRunnable);
    }


    @Override
    protected InputListener getEventListener(String text, Runnable onFinish) {
        return new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try{
                    onFinish.run();
                }catch (Exception e){

                }
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
