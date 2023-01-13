package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.interfaces.OnClickCallback;
import com.mygdx.game.interfaces.OnClickBooleanCallback;
import com.mygdx.game.model.CompetitionOpponent;
import com.mygdx.game.model.Container;
import com.mygdx.game.model.enums.Comp;
import com.mygdx.game.model.enums.InventoryType;
import com.mygdx.game.model.items.OnItemClick;
import com.mygdx.game.rooms.CompetitionRoom;
import com.mygdx.game.rooms.GymRoom;
import com.mygdx.game.rooms.MapRoom;
import com.mygdx.game.rooms.ParkRoom;
import com.mygdx.game.rooms.RoomRoom;
import com.mygdx.game.rooms.ShopRoom;
import com.mygdx.game.rooms.WorkRoom;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class MyGdxGame implements ApplicationListener, InterScreenCommunication {

    BaseRoom stage;
    Player player;
    String saves;
    int adCount = 0;

    private IActivityRequestHandler myRequestHandler;

    public IActivityRequestHandler getMyRequestHandler() {
        return myRequestHandler;
    }

    public MyGdxGame() {
     //   myRequestHandler = handler;
       // saves = player;
    }

    public MyGdxGame(IActivityRequestHandler handler, String player) {
        myRequestHandler = handler;
        saves = player;
    }

    @Override
    public void create() {
        Preffics.getInstance();
        this.player = new Player();
        if(saves != null){
            try {
                JSONObject jsonObject = new JSONObject(saves);
                this.player.fromJson(jsonObject);
                openRoom(jsonObject.optString("ROOM_TAG"));
                saves = null;
            } catch (JSONException e) {
                e.printStackTrace();
                openGym();
            }
        }else openGym();
    }

    private void openRoom(String room_tag) {
        if(Objects.equals(room_tag, "gym")){
            openGym();
        }else if(Objects.equals(room_tag, "map")){
            openMap();
        }else if(Objects.equals(room_tag, "park")){
            openPark();
        }else if(Objects.equals(room_tag, "room")){
            openRoom();
        }else if(Objects.equals(room_tag, "shop")){
            openShop();
        }else if(Objects.equals(room_tag, "work")){
            openWork();
        }else if(Objects.equals(room_tag, "competition")){
            openCompetition();
        }else{
            openGym();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.onRender();
        if(player != null){
            showHud();
        }
    }

    @Override
    public void showHud() {
        myRequestHandler.showHud(player);
    }

    @Override
    public void openStats(Runnable pauseGame) {
        myRequestHandler.openStats(player, pauseGame);
    }

    @Override
    public void openPerkMenu(Runnable pauseGame) {
        myRequestHandler.openPerkMenu(player, pauseGame);
    }

    @Override
    public void savePlayer(String toString) {
        myRequestHandler.savePlayer(toString);
    }

    @Override
    public void pause() {
        stage.onPause();
    }

    @Override
    public void resume() {
        stage.onRender();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void showToast(String msg) {
        myRequestHandler.showToast(msg);
    }

    @Override
    public void showPlayerList(List<CompetitionOpponent> playerList, Comp status, OnClickCallback runnable) {
        myRequestHandler.showPlayers(playerList, status, runnable);
    }

    @Override
    public void showNextSetMenu(Player player, int currentSet, List<CompetitionOpponent> playerList, OnClickCallback onFirstClick, OnClickCallback onClose) {
        myRequestHandler.showNextSetMenu(player, currentSet, playerList, onFirstClick, onClose);
    }

    @Override
    public void showDialog(String title, String subtitle, OnClickCallback onClose, OnClickCallback onAgree) {
        myRequestHandler.showDialog(title, subtitle, onClose, onAgree);
    }

    @Override
    public void showProgressBar(String title, OnClickCallback onProgressEnd, OnClickBooleanCallback onProgressUpdate, OnClickCallback onClose) {
        myRequestHandler.showProgressBar(title, onProgressEnd, onProgressUpdate, onClose);
    }

    @Override
    public void showDialog(String title, String subtitle, String agreeText, String closeText, OnClickCallback onClose, OnClickCallback onAgree) {
        myRequestHandler.showDialog(title, subtitle, agreeText, closeText, onClose, onAgree);
    }


    @Override
    public void openMap() {
        setStage(
                new MapRoom(this, player),
                new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        stage.onTouchDown();
                        return super.touchDown(event, x, y, pointer, button);
                    }
                }
        );
    }

    private void setStage(BaseRoom room, InputListener inputListener) {
        //if(stage == null /*|| adCount != 5*/){
            stage = room;
            stage.addListener(inputListener);
            adCount++;
        //}else {
            /*adCount = 0;
            myRequestHandler.showAd(
                    () -> {
                        stage = room;
                        stage.addListener(inputListener);
                    }
            );*/
     //   }
    }

    @Override
    public void openGym() {
        setStage(
                new GymRoom(this, player),
                new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        stage.onTouchDown();
                        return super.touchDown(event, x, y, pointer, button);
                    }
                }
        );
    }

    @Override
    public void openRoom() {
        setStage(
                new RoomRoom(this, player),
                new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        stage.onTouchDown();
                        return super.touchDown(event, x, y, pointer, button);
                    }
                }
        );
    }

    @Override
    public void openShop() {
        setStage(
                new ShopRoom(this, player),
                new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        stage.onTouchDown();
                        return super.touchDown(event, x, y, pointer, button);
                    }
                }
        );
    }


    @Override
    public void openWork() {
        setStage(
                new WorkRoom(this, player),
                new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        stage.onTouchDown();
                        return super.touchDown(event, x, y, pointer, button);
                    }
                }
        );
    }


    @Override
    public void openPark() {
        setStage(
                new ParkRoom(this, player),
                new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        stage.onTouchDown();
                        return super.touchDown(event, x, y, pointer, button);
                    }
                }
        );
    }

    @Override
    public void openCompetition() {
        setStage(
                new CompetitionRoom(this, player),
                new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        //stage.onTouchDown();
                        return super.touchDown(event, x, y, pointer, button);
                    }
                }
        );

    }

    @Override
    public void openOptions() {
        myRequestHandler.openOptions();
    }

    @Override
    public void openRefrigerator(Player player, OnItemClick onItemClick, Runnable runnable) {
        myRequestHandler.openRefrigerator(player, onItemClick, runnable);
    }

    @Override
    public void showBuyMenu(InventoryType type, Container inventoryContainer, Container shopContainer, OnItemClick onBuyRunnable, Runnable onCancel) {
        myRequestHandler.openShowBuyMenu(type, inventoryContainer, shopContainer, onBuyRunnable, onCancel);
    }


    @Override
    public void openInventory(Runnable runnable) {
        myRequestHandler
                .openInventory(
                        player,
                        runnable,
                        (item)-> {
                            item.onUse(player);
                            player.inventoryManager.inventory.removeItem(item);
                            openInventory(runnable);
                        });
    }

}

