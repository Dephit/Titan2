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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONException;

import java.util.ArrayList;

public abstract class BaseRoom extends Stage {

    private final boolean enableMapDrawing = false;
    protected boolean callOnClose = false;

    public Runnable pauseGame() {
        pause = true;
        return () -> pause = false;
    }

    public Runnable pauseGame(Runnable runnable) {
        pause = true;
        return () -> {
            runnable.run();
            pause = false;
        };
    }

    protected ArrayList<Npc> npcs = new ArrayList<>();

    protected Group objectGroup, buttonGroup/*, hudGroup = new Group()*/;

    public Player player;
    protected String ROOM_TAG = "";
    protected String BACKGROUND_PATH_TAG;

    private Texture background;

    private Texture tex2, tex3, tex4;
    private Pixmap pix2, pix3, pix4;

    public InterScreenCommunication interScreenCommunication;

    private OrthographicCamera camera;
    protected Vector3 touchPos;
    protected Skin skin;
    protected boolean pause = false;

    public BaseRoom(String tag) {
        super(new FitViewport(Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT));
        ROOM_TAG = tag;
        BACKGROUND_PATH_TAG = "screens/" + ROOM_TAG + "/" + ROOM_TAG + ".png";
        init();
    }

    public BaseRoom(InterScreenCommunication communication, String tag, Player _player) {
        super(new FitViewport(Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT));
        if (_player != null) {
            player = _player;
            try {
                communication.savePlayer(
                    player.toJson()
                        .put("ROOM_TAG", tag)
                        .toString()
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            npcs.add(player);
        }
        ROOM_TAG = tag;
        interScreenCommunication = communication;
        BACKGROUND_PATH_TAG = "screens/" + ROOM_TAG + "/" + ROOM_TAG + ".png";
        init();
        if (_player != null) {
            objectGroup.addActor(player);
        }
        addActor(objectGroup);
        addActor(buttonGroup);
    }

    public void init() {
        Preffics.getInstance().clearAssets();
        Preffics.getInstance()
            .load(BACKGROUND_PATH_TAG, Texture.class)
            .load("screens/dialogs/message.png", Texture.class)
            .load("screens/buttons/buttons.atlas", TextureAtlas.class);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Preffics.SCREEN_WIDTH, Preffics.SCREEN_HEIGHT);
        touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        if (enableMapDrawing) {
            pix2 = createProceduralPixmap(1, 1, 1, 0, 0);
            tex2 = new Texture(pix2);
            pix3 = createProceduralPixmap(1, 1, 0, 1, 0);
            tex3 = new Texture(pix3);
            pix4 = createProceduralPixmap(1, 1, 0, 0, 1);
            tex4 = new Texture(pix4);
        }

        Gdx.input.setInputProcessor(this);
        Preffics.getInstance().createMap(ROOM_TAG);
        if (player != null) {
            player.clearPath();
        }
        objectGroup = new Group();
        buttonGroup = new Group();
    }


    public Pixmap createProceduralPixmap(int width, int height, float r, float g, float b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fill();
        return pixmap;
    }


    public void showDialog(Message message) {
        interScreenCommunication.showDialog(
            message.title,
            message.text,
            object -> {
            },
            object -> {
                message.onClick();
                showDialog(message);
            }
        );
    }

    public BaseRoom(FitViewport viewport) {
        super(viewport);
        init();
    }

    public Language getLanguage() {
        return Preffics.getInstance().getLanguage();
    }

    public void onTouchDown() {
        touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        Preffics preffics = Preffics.getInstance();
        double[][] mapArr = preffics.mapArr;
        try {
            if (mapArr[(int) touchPos.y / preffics.mapCoordinateCorrector][(int) touchPos.x / preffics.mapCoordinateCorrector] != -1) {
                player.goToDestination((int) touchPos.x, (int) touchPos.y, 0, 0, PlayerCondition.stay);
            }
        } catch (Exception ignored) {

        }
    }

    public void resize(int width, int height) {
        getViewport().update(width, height);
        camera.update();
    }

    public void onRender() {
        Preffics preffics = Preffics.getInstance();
        camera.update();
        getBatch().setProjectionMatrix(camera.combined);

        getBatch().begin();
        if (preffics.isLoaded()) {
            onLoaded(preffics);
        } else {
            onLoading(preffics);
        }
        if (enableMapDrawing) {
            showPath(preffics);
        }

        getBatch().end();
        draw();
        if (!pause) {
            act(Gdx.graphics.getDeltaTime());
        }
        if (callOnClose) {
            onClose();
        }
    }

    public void onClose() {

    }


    private void showPath(Preffics preffics) {
        try {
            for (int i = 0; i < preffics.mapSize; i++) {
                for (int j = 0; j < preffics.mapSize * 4; j++) {
                    if (preffics.mapArr[i][j] == 0)
                        getBatch().draw(tex3, j * preffics.mapCoordinateCorrector, i * preffics.mapCoordinateCorrector, 8, 8);
                    else
                        getBatch().draw(tex4, j * preffics.mapCoordinateCorrector, i * preffics.mapCoordinateCorrector, 8, 8);
                }
                getBatch().draw(tex2, player.getX() / preffics.mapCoordinateCorrector, player.getY() / preffics.mapCoordinateCorrector, 8, 8);
                try {
                    for (Grid2d.MapNode mapNode : player.path) {
                        getBatch().draw(tex2, mapNode.x * preffics.mapCoordinateCorrector, mapNode.y * preffics.mapCoordinateCorrector, 8, 8);
                    }
                } catch (NullPointerException ignored) {
                }
            }
        } catch (Exception e) {

        }
    }

    private void onLoading(Preffics preffics) {
        preffics.updateLoading();
        showText(preffics.getLanguage().loading + ": " + ((int) preffics.getLoadingProgress()), Preffics.SCREEN_WIDTH / 2 - 250, Preffics.SCREEN_HEIGHT / 2, 2, Color.CORAL);
    }

    protected void showText(String str, int x, int y, int scale, Color color) {
        BitmapFont textFont = FontFactory.getInstance().getFont(Preffics.getInstance().getLocale());
        textFont.getData().setScale(scale);
        textFont.setColor(color);
        textFont.draw(getBatch(), str, x, y);
        textFont.getData().setScale(1);
    }

    private void onLoaded(Preffics preffics) {
        if (background == null) {
            background = preffics.getByPath(BACKGROUND_PATH_TAG, Texture.class);
            createObjects(preffics);
            createButtons();
        } else {
            for (Actor objectData : objectGroup.getChildren()) {
                if (objectData.getClass() == ObjectData.class) {
                    ObjectData obj = ((ObjectData) objectData);
                    boolean oneFrame = false;
                    for (Npc npc : npcs) {
                        if (obj.name.equalsIgnoreCase(npc.playerCondition.name())) {
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
        try {
            TextureAtlas textureAtlas = new TextureAtlas(preffics.getPath() + "screens/" + ROOM_TAG + "/objects/objects.atlas");
            final ArrayList<ObjectData> objectList = preffics.fromObjectFromJson("screens/" + ROOM_TAG + "/objects/objects.json", ArrayList.class);
            for (ObjectData object : objectList) {
                object.setAtlas(textureAtlas);
                object.addListener(getEventListener(object.name, () -> object.setCertainFrame(true)));

                log(object.name + ", " + object.x + ", " + object.y);
                objectGroup.addActor(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void drawOrder() {
        for (int j = 0; j < objectGroup.getChildren().size; j++) {
            for (int i = 0; i < objectGroup.getChildren().size - 1; i++) {
                Actor iActor = objectGroup.getChildren().get(i);
                Actor iActorNext = objectGroup.getChildren().get(i + 1);
                if (iActor.getY() < objectGroup.getChildren().get(i + 1).getY())
                    objectGroup.getChildren().swap(i, i + 1);
                orderExceptions(i, j);
            }
        }
    }

    protected void orderExceptions(int i, int j) {
    }

    TextButton.TextButtonStyle getTextButtonStyleFromFile(Skin skin, String name) {
        final TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontFactory.font;
        textButtonStyle.fontColor = Color.BLACK;

        textButtonStyle.downFontColor = Color.BLUE;
        textButtonStyle.up = skin.getDrawable(name);
        textButtonStyle.down = skin.getDrawable(name);
        skin.dispose();
        return textButtonStyle;
    }

    private void createButtons() {
        skin = new Skin();

        TextureAtlas buttonAtlas = Preffics.getInstance().getByPath("screens/buttons/buttons.atlas", TextureAtlas.class);
        skin.addRegions(buttonAtlas);
        setCommonButtons();
    }

    public void setCommonButtons() {
        addButton("map", "map", "", 10, 800, 125, 125, 1f, () -> interScreenCommunication.openMap());
        addButton("optionButton", "optionButton", "", 10, 940, 125, 125, 1f, () -> interScreenCommunication.openOptions());
        addButton("optionButton", "optionButton", "", 10, 660, 125, 125, 1f, () -> interScreenCommunication.openInventory(pauseGame()));
        addButton("optionButton", "optionButton", "", 10, 520, 125, 125, 1f, () -> interScreenCommunication.openPerkMenu(pauseGame()));
    }

    public void addButton(String name, String style, String text, int x, int y, int w, int h, float scale, Runnable runnable) {
        buttonGroup.addActor(getTextButton(name, style, text, x, y, w, h, scale, runnable));
    }

    void log(String msg) {
        System.out.println(ROOM_TAG + ": " + msg);
    }

    protected abstract InputListener getEventListener(String name, Runnable runnable);

    protected TextButton getTextButton(String name, String styleName, String text, int x, int y, int w, int h, float scale, Runnable onClick) {
        final TextButton textButton = new TextButton(text, getTextButtonStyleFromFile(skin, styleName));
        textButton.setName(name);
        textButton.addListener(getEventListener(textButton.getName(), onClick));
        textButton.getLabel().setFontScale(scale);
        textButton.setBounds(x, y, w, h);
        return textButton;
    }
}


