package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRoom extends Stage  {

    ArrayList<Npc> npcs = new ArrayList<>();

    public Player player;
    protected String ROOM_TAG = "";
    protected String BACKGROUND_PATH_TAG;

    private Texture background;

    private Texture tex2, tex3, tex4;
    private Pixmap pix2, pix3, pix4;


    private OrthographicCamera camera;
    protected Vector3 touchPos;

    public BaseRoom(String tag) {
        super(new FitViewport(Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT));
        ROOM_TAG = tag;
        BACKGROUND_PATH_TAG = "screens/" + ROOM_TAG + "/" + ROOM_TAG + ".png";
        init();
    }

    public BaseRoom(String tag, Player _player) {
        super(new FitViewport(Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT));
        player = _player;
        npcs.add(player);
        ROOM_TAG = tag;
        BACKGROUND_PATH_TAG = "screens/" + ROOM_TAG + "/" + ROOM_TAG + ".png";
        init();
        addActor(player);
    }

    public void init() {
        Preffics.getInstance().clearAssets();
        Preffics.getInstance()
                .load(BACKGROUND_PATH_TAG, Texture.class)
                .load("screens/dialogs/message.png", Texture.class);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT);
        touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        pix2 = createProceduralPixmap(1, 1, 1, 0, 0);
        tex2 = new Texture(pix2);
        pix3 = createProceduralPixmap(1, 1, 0, 1, 0);
        tex3 = new Texture(pix3);
        pix4 = createProceduralPixmap(1, 1, 0, 0, 1);
        tex4 = new Texture(pix4);

        Gdx.input.setInputProcessor(this);
        Preffics.getInstance().createMap(ROOM_TAG);
        player.clearPath();
    }

    public Pixmap createProceduralPixmap (int width, int height, float r, float g, float b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fill();
        return pixmap;
    }

    public BaseRoom(FitViewport viewport) {
        super(viewport);
        init();
    }

    public void onTouchDown(InputEvent event, float x, float y, int pointer, int button) {
        touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        Preffics preffics = Preffics.getInstance();
        double[][] mapArr = preffics.mapArr;
        try{
            if (mapArr[(int) touchPos.y / preffics.mapCoordinateCorrector][(int) touchPos.x / preffics.mapCoordinateCorrector] != -1) {
                player.setPath((int)touchPos.x, (int)touchPos.y, 0, 0, PlayerCondition.stay);
            }
        }catch (Exception e){

        }
    }

    public void resize(int width, int height) {
        getViewport().update(width,height);
        camera.update();
    }

    public void onRender() {
        Preffics preffics = Preffics.getInstance();
        camera.update();
        getBatch().setProjectionMatrix(camera.combined);
        getBatch().begin();
        if(preffics.isLoaded()) {
            onLoaded(preffics);
        } else {
            onLoading(preffics);
        }

        showPath(preffics);
        getBatch().end();
        draw();
        act(Gdx.graphics.getDeltaTime());
    }

    private void showPath(Preffics preffics) {
        for(int i = 0; i < preffics.mapSize; i++) {
            for (int j = 0; j < preffics.mapSize * 4; j++) {
                if (preffics.mapArr[i][j] == 0)
                    getBatch().draw(tex3, j * preffics.mapCoordinateCorrector, i * preffics.mapCoordinateCorrector, 8, 8);
                else
                    getBatch().draw(tex4, j * preffics.mapCoordinateCorrector, i * preffics.mapCoordinateCorrector, 8, 8);
            }
            getBatch().draw(tex2, (int) player.getX() / preffics.mapCoordinateCorrector, (int) player.getY() / preffics.mapCoordinateCorrector, 8, 8);
            try {
                for (Grid2d.MapNode mapNode : player.path) {
                    getBatch().draw(tex2, mapNode.x * preffics.mapCoordinateCorrector, mapNode.y * preffics.mapCoordinateCorrector, 8, 8);
                }
            } catch (NullPointerException ignored) {
            }
        }
    }

    private void onLoading(Preffics preffics) {
        preffics.updateLoading();
        showText( preffics.getLanguage().loading + ": " + preffics.getLoadingProgress(), 500, 500, 2, Color.CORAL);
    }

    protected void showText(String str, int x, int y, int scale, Color color) {
        BitmapFont textFont = FontFactory.getInstance().getFont(Preffics.getInstance().getLocale());
        textFont.getData().setScale(scale);
        textFont.setColor(color);
        textFont.draw(getBatch(), str, x, y);
        textFont.getData().setScale(1);
    }

    private void onLoaded(Preffics preffics) {
        if(background == null) {
            background = preffics.getByPath(BACKGROUND_PATH_TAG, Texture.class);
            createObjects(preffics);
        }else {
            for(Actor objectData: getActors()){
                if(objectData.getClass() == ObjectData.class){
                    ObjectData obj = ((ObjectData) objectData);
                    boolean oneFrame = false;
                    for (Npc npc: npcs){
                        if (obj.name.toLowerCase().equals(npc.playerCondition.name().toLowerCase())) {
                            oneFrame = true;
                            break;
                        }
                    }
                    obj.setCertainFrame(oneFrame);
                }
            }
            getBatch().draw(background, 0, 0, getWidth(), getHeight());
            drawOrder();
        }
    }

    public void onPause() {
    }

    @Override
    public void clear() {
        super.clear();
        if (background != null)
            background.dispose();
    }

    private void createObjects(Preffics preffics) {
        TextureAtlas textureAtlas = new TextureAtlas(preffics.getPath() + "screens/" + ROOM_TAG + "/objects/objects.atlas");
        final ArrayList<ObjectData> objectList = preffics.fromObjectFromJson("screens/" + ROOM_TAG + "/objects/objects.json", ArrayList.class);
        for (ObjectData object: objectList){
            object.setAtlas(textureAtlas);
            object.addListener(getEventListener(object.name, () -> object.setCertainFrame(true)));

            log(object.name + ", " + object.x + ", " + object.y);
            addActor(object);
        }
    }

    protected void drawOrder() {
        for (int j = 0; j < getActors().size; j++) {
            for (int i = 0; i < getActors().size - 1; i++) {
                Actor iActor = getActors().get(i);
                Actor iActorNext = getActors().get(i + 1);
                if (iActor.getY() < getActors().get(i + 1).getY())
                    getActors().swap(i, i + 1);

                orderExceptions(i, j);
            }
        }
    }

    protected void orderExceptions(int i, int j) {
        if (getActors().get(i).getName() != null) {
            if (getActors().get(i).getName().contains("message")) {
                getActors().swap(i, getActors().size - 1);
            }
        }
    }

    void log(String msg){
        System.out.println(ROOM_TAG + ": " + msg);
    }

    protected abstract InputListener getEventListener(String name, Runnable runnable);
}

