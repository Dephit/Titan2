package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class BaseRoom extends Stage {

    public static List<Grid2d.MapNode> path = new LinkedList<Grid2d.MapNode>();

    private Player player;
    protected String ROOM_TAG = "";
    protected String BACKGROUND_PATH_TAG;

    private Texture background;

    private Texture tex2, tex3, tex4;
    private Pixmap pix2, pix3, pix4;


    private OrthographicCamera camera;
    private Vector3 touchPos;
    private int mapCoorinateCorrector;
    private double[][] mapArr;
    private Grid2d map2d;

    public BaseRoom(String tag) {
        super(new FitViewport(Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT));
        ROOM_TAG = tag;
        BACKGROUND_PATH_TAG = "screens/" + ROOM_TAG + "/" + ROOM_TAG + ".png";
        init();
    }

    public BaseRoom(String tag, Player _player) {
        super(new FitViewport(Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT));
        player = _player;
        ROOM_TAG = tag;
        BACKGROUND_PATH_TAG = "screens/" + ROOM_TAG + "/" + ROOM_TAG + ".png";
        init();
        player.setPath(path);
        addActor(player);
    }

    private void createMap() {
        int mapSize = 14;
        mapCoorinateCorrector = 50;
        mapArr = new double[mapSize][mapSize * 4];
        // saveRoomMap();
        RestoreMap();

        map2d = new Grid2d(mapArr, false);
        if (path != null)
            path.clear();
    }

    private void RestoreMap() {
        try {
            ArrayList<Coordinates> coordinates = Preffics.getInstance().fromObjectFromJson("screens/" + ROOM_TAG + "/" + ROOM_TAG + ".json", ArrayList.class);
            for (Coordinates coordinate : coordinates) {
                log(coordinate.x + " : " + coordinate.y);
                mapArr[coordinate.x][coordinate.y] = -1;
            }
        } catch (GdxRuntimeException ignored) {

        }
    }

    public void init() {
        Preffics.getInstance().clearAssets();
        Preffics.getInstance()
                .load(BACKGROUND_PATH_TAG, Texture.class);
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
        createMap();
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
        setPath((int)touchPos.x, (int)touchPos.y, 0, 0);
        camera.unproject(touchPos);
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
        getBatch().end();
        draw();
        act(Gdx.graphics.getDeltaTime());
    }

    private void onLoading(Preffics preffics) {
        preffics.updateLoading();
        showText( preffics.getLanguage().loading + ": " + preffics.getLoadingProgress(), 500, 500, 2, Color.CORAL);
    }

    private void showText(String str, int x, int y, int scale, Color color) {
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
            log(object.name + ", " + object.x + ", " + object.y);
            addActor(object);
        }
    }

    private void drawOrder() {
        for (int j = 0; j < getActors().size; j++) {
            for (int i = 0; i < getActors().size - 1; i++) {
                if (getActors().get(i).getY() < getActors().get(i + 1).getY())
                    getActors().swap(i, i + 1);
            }
        }
    }

    void log(String msg){
        System.out.println(ROOM_TAG + ": " + msg);
    }

    private void setPath(int xGoal, int yGoal, int xDestination, int yDestination) {
        int pX = (int) player.getX() / mapCoorinateCorrector, pY = (int) player.getY() / mapCoorinateCorrector;

        if (path != null)
            if (path.size() > 0)
                if (path.get(0).x != pX || path.get(0).y != pY) {
                    player.lastWalkablePosition.set(player.getX(), player.getY());
                }

        if (xGoal / mapCoorinateCorrector != map2d.xGoal || yGoal / mapCoorinateCorrector != map2d.yGoal ){
            player.setPosition(player.lastWalkablePosition.x, player.lastWalkablePosition.y);
            player.ceilPos();

            path = map2d.findPath(
                    pX,
                    pY,
                    xGoal / mapCoorinateCorrector,
                    yGoal / mapCoorinateCorrector);
        }
        /*player.doExercise = true;
        player.setPlayersAction(playerCondition, xDestination, yDestination);*/
    }

}

