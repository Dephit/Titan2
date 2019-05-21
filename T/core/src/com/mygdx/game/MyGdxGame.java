package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import static com.mygdx.game.MyGdxGame.Screens.gym;
import static com.mygdx.game.MyGdxGame.Screens.map;
import static com.mygdx.game.MyGdxGame.Screens.menu;
import static com.mygdx.game.MyGdxGame.Screens.options;
import static com.mygdx.game.MyGdxGame.Screens.room;
import static com.mygdx.game.MyGdxGame.Screens.stats;
import static com.mygdx.game.MyGdxGame.Screens.work;
import static com.mygdx.game.MyGdxGame.Windows.chouseSquatMenu;
import static com.mygdx.game.MyGdxGame.Windows.none;
import static com.mygdx.game.MyGdxGame.Windows.refregirator;
import static com.mygdx.game.MyGdxGame.Windows.workMenu;
import static com.mygdx.game.MyMethods.createProceduralPixmap;
import static com.mygdx.game.MyMethods.getJson;
import static com.mygdx.game.MyMethods.getPath;
import static com.mygdx.game.MyMethods.getTextButtonStyleFromFile;

public class MyGdxGame implements ApplicationListener {
    private OrthographicCamera camera;
    private Vector3 touchPos;
    private Player player;
    private static int mapSize;
    public static int mapCoorinateCorrector;

    private Texture tex2, tex3, tex4;
    private Pixmap pix2, pix3, pix4;
    public static List<Grid2d.MapNode> path = new LinkedList<Grid2d.MapNode>();
    public static List<Grid2d.MapNode> path3 = new LinkedList<Grid2d.MapNode>();
    private static Group screenButtons = new Group();
    private static Array<Objects> playObjects = new Array<Objects>();
    private static Array<Objects> allObjects = new Array<Objects>();

    private Grid2d map2d;
    private double[][] mapArr;
    private Group objectDrawOrderGroup = new Group(), hudGroup = new Group();

    public static Locale locale = null;
    private Group windowGroup = new Group();
    private ArrayList<TextButton> buttonsArr = new ArrayList<TextButton>();

    public enum Screens {
        gym, room, map, work, shop, menu, options, stats
    }

    public enum Windows {
        none, refregirator, chouseSquatMenu, workMenu
    }

    static class Coordinates {
        public int x, y;

        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinates() {
        }
    }

    private static Stage stage;
    private Screens currentScreen, previousScreen;
    private Windows currentWindow = none;
    private Texture background;

    @Override
    public void create() {
        FontFactory.getInstance().initialize();
        String language = "ru";
        if (language.equals("ru"))
            locale = new Locale("ru", "RU");
        else
            locale = new Locale("es", "ES");

        stage = new Stage(new StretchViewport(1920, 1080));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        createButtons();

        touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        player = new Player();
        setUpRoom(menu);

        for (StatBar stat : player.stats) {
            hudGroup.addActor(stat);
        }
        stage.addActor(hudGroup);
        stage.addActor(windowGroup);

        pix2 = createProceduralPixmap(1, 1, 1, 0, 0);
        tex2 = new Texture(pix2);
        pix3 = createProceduralPixmap(1, 1, 0, 1, 0);
        tex3 = new Texture(pix3);
        pix4 = createProceduralPixmap(1, 1, 0, 0, 1);
        tex4 = new Texture(pix4);
        Gdx.input.setInputProcessor(stage);

    }

    private void createMap() {
        mapSize = 10;
        mapCoorinateCorrector = 50;
        mapArr = new double[mapSize][mapSize * 4];
        // saveRoomMap();
        RestoreMap();
        map2d = new Grid2d(mapArr, false);
        if (path != null)
            path.clear();
    }

    private void RestoreMap() {
        FileHandle file = null;
        ArrayList<Coordinates> coordinates;
        try {
            file = Gdx.files.internal(getPath() + "screens/" + currentScreen + "/" + currentScreen + ".json");
            String stringCoordinates = file.readString();
            coordinates = (json.fromJson(ArrayList.class, stringCoordinates));

            for (Coordinates coordinate : coordinates) {
                mapArr[coordinate.x][coordinate.y] = -1;
            }
        } catch (GdxRuntimeException ignored) {
        }
    }

    // debug fun
    private void saveRoomMap() {
        ArrayList<Coordinates> coordinates = new ArrayList();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize * 4; j++) {
                if (mapArr[i][j] == -1) {
                    Coordinates coor = new Coordinates(i, j);
                    coordinates.add(coor);
                }
            }
        }
        FileHandle file = null;
        file = Gdx.files.local(getPath() + "screens/" + currentScreen + "/" + currentScreen + ".json");
        // file.writeString("", false);
        String score = json.toJson(coordinates);
        file.writeString(score, true);
    }

    private void createButtons() {
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal(getPath() + "screens/buttons/buttons.atlas"));
        skin.addRegions(buttonAtlas);

        String buttonList = getJson("screens/buttons/buttons.json");
        String textList = getJson("screens/buttons/buttonText/buttonText" + locale.getCountry() + ".json");

        final ArrayList<ButtonData> buttonDataArrayList = json.fromJson(ArrayList.class, buttonList);
        final ArrayList<ButtonText> buttonTextArrayList = json.fromJson(ArrayList.class, textList);

        for (final ButtonData buttonData : buttonDataArrayList) {
            final TextButton textButton = new TextButton("", getTextButtonStyleFromFile(skin, buttonData.name));
            textButton.setName(buttonData.name);
            textButton.setBounds(buttonData.x, buttonData.y, buttonData.width, buttonData.height);
            textButton.addListener(getListener(textButton.getName()));

            for (ButtonText buttonText : buttonTextArrayList) {
                if (buttonText.buttonName.equals(textButton.getName())) {
                    textButton.setText(buttonText.text);
                    break;
                }
            }
            buttonsArr.add(textButton);
        }
        skin.dispose();
    }

    static class ButtonText {
        String buttonName;
        String text;

        public ButtonText(String buttonName, String text) {
            this.buttonName = buttonName;
            this.text = text;
        }

        public ButtonText() {
        }
    }

    static class ButtonData {
        public String name;
        public int x, y, width, height;
        public List<String> screenLIst = new ArrayList<String>();

        public ButtonData(TextButton tb) {
            this.name = tb.getName();
            this.x = (int) tb.getX();
            this.y = (int) tb.getY();
            this.width = (int) tb.getWidth();
            this.height = (int) tb.getHeight();
        }

        public ButtonData() {
        }
    }

    static class ObjectData {
        public String name;
        public int x, y, width, height, rows, cols, time;
        public List<int[]> animations = new ArrayList<int[]>();

        public ObjectData(String name, int x, int y, int width, int height, int rows, int cols, int time,
                List<int[]> animations) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.rows = rows;
            this.cols = cols;
            this.time = time;
            this.animations = animations;
        }

        public ObjectData() {
        }
    }

    private void setUpRoom(Screens string) {
        previousScreen = currentScreen;
        currentScreen = string;
        setUpWindow(none);
        clearStage();
        background = MyMethods.LoadImg("screens/" + string + "/" + string + ".png");

        try {
            createObjects(string.toString());
        } catch (GdxRuntimeException ignored) {
        }

        loadButtons(string);

        if (currentScreen != map && currentScreen != menu && currentScreen != options && currentScreen != stats) {
            objectDrawOrderGroup.addActor(player);
        }

        for (Objects playObject : playObjects) {
            objectDrawOrderGroup.addActor(playObject);
        }

        stage.addActor(objectDrawOrderGroup);
        stage.addActor(screenButtons);

        for (Actor actor : screenButtons.getChildren()) {
            if (actor.getName().equals("statsButton")) {
                hudGroup.setVisible(true);
                player.setStatVisible();
            }
        }
        createMap();
    }

    private void createObjects(String string) {
        String objectList = getJson("screens/" + string + "/objects/objects.json");
        final ArrayList<ObjectData> objectDataArrayList = json.fromJson(ArrayList.class, objectList);

        for (final ObjectData objectData : objectDataArrayList) {
            boolean needLoad=false;
          /*  for (Objects object : allObjects) {
                if(object.getName().equals(objectData.name)) {
                    playObjects.add(object);
                    needLoad = true;
                    break;
                }
            }*/
            if(!needLoad) {
                Objects objects = new Objects("screens/" + string + "/objects/", objectData);
                playObjects.add(objects);
                allObjects.add(objects);
            }
        }
    }

    private void loadButtons(Screens string) {
        String buttonList = getJson("screens/" + string + "/buttons/" + "buttons.json");
        final ArrayList<ButtonData> buttonDataArrayList = json.fromJson(ArrayList.class, buttonList);

        for (final ButtonData buttonData : buttonDataArrayList) {
            for (TextButton textButton : buttonsArr) {

                if (buttonData.name.equals(textButton.getName())) {

                    if (buttonData.x != 0 && buttonData.y != 0 && buttonData.width != 0 && buttonData.height != 0)
                        textButton.setBounds(buttonData.x, buttonData.y, buttonData.width, buttonData.height);
                    screenButtons.addActor(textButton);
                    break;
                }
            }
        }
    }

    private void clearStage() {
        objectDrawOrderGroup.clear();
        playObjects.clear();
        hudGroup.setVisible(false);
        screenButtons.clear();

        if (background != null)
            background.dispose();

        if (previousScreen != options && previousScreen != stats)
            player.setParameters();
    }

    private void setUpWindow(Windows window) {
        currentWindow = window;

        if (window == none) {
            windowGroup.clear();
        } else {
            Window w;
            try {
                String s = getJson("screens/windows/" + window.toString() + "/coord.json");
                ButtonData bounds = json.fromJson(ButtonData.class, s);
                w = new Window(bounds.x, bounds.y, bounds.width, bounds.height, currentWindow.toString());
            } catch (GdxRuntimeException e) {
                w = new Window(1920 / 2 - 750f, 1080 / 2 - 350f, 1500, 700, currentWindow.toString());
            } catch (SerializationException exception) {
                w = new Window(640 / 2 - 750f, 1080 / 2 - 350f, 1500, 700, currentWindow.toString());
            }
            w.loadButtons(window.toString(), buttonsArr);
            windowGroup.addActor(w);
            windowGroup.addActor(w.thisGroup);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        keyPressing();
        touches();
        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, 1920, 1080);

        /*
         * for(int i=0;i<mapSize;i++){ for(int j=0;j<mapSize*4;j++){ if(mapArr[i][j]==0)
         * stage.getBatch().draw(tex3,j*mapCoorinateCorrector,i*mapCoorinateCorrector,8,
         * 8); else
         * stage.getBatch().draw(tex4,j*mapCoorinateCorrector,i*mapCoorinateCorrector,8,
         * 8); //font.draw(stage.getBatch(),j+" "+i,j*mapCoorinateCorrector,i*
         * mapCoorinateCorrector); }
         * 
         * } stage.getBatch().draw(tex2, (int)player.getX()/mapCoorinateCorrector,
         * (int)player.getY()/mapCoorinateCorrector,8,8); try { for (Grid2d.MapNode
         * mapNode : path) { stage.getBatch().draw(tex2,
         * mapNode.x*mapCoorinateCorrector,mapNode.y*mapCoorinateCorrector,8,8); }
         * }catch (NullPointerException e){}
         */

        drawOrder();
        stage.getBatch().end();

        stage.draw();

        if (currentWindow == none)
            stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        // textDrawing( stage.getBatch(),1+"",1825,990,1.5f, Color.WHITE);
        stage.getBatch().end();
    }

    private void keyPressing() {

    }

    private void drawOrder() {
        for (int j = 0; j < objectDrawOrderGroup.getChildren().size; j++) {
            for (int i = 0; i < objectDrawOrderGroup.getChildren().size - 1; i++) {
                if (objectDrawOrderGroup.getChildren().get(i).getY() < objectDrawOrderGroup.getChildren().get(i + 1)
                        .getY())
                    objectDrawOrderGroup.getChildren().swap(i, i + 1);
                if (objectDrawOrderGroup.getChildren().get(i).getName().equals("player")
                        && player.getPlayerCondition().equals(Player.PlayerCondition.bench))
                    objectDrawOrderGroup.getChildren().swap(i, objectDrawOrderGroup.getChildren().size - 1);
            }
        }
        stage.getActors().swap(stage.getActors().indexOf(hudGroup, true), stage.getActors().indexOf(windowGroup, true));
        stage.getActors().swap(stage.getActors().indexOf(windowGroup, true), stage.getActors().size - 1);
    }

    private void touches() {
        if (Gdx.input.justTouched()) {
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            try {
                if (mapArr[(int) touchPos.y / mapCoorinateCorrector][(int) touchPos.x / mapCoorinateCorrector] != -1) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, (int) touchPos.x / mapCoorinateCorrector,
                            (int) touchPos.y / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.stay, 0, 0);
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private InputListener getListener(String name) {
        InputListener listener = new InputListener();

        // QUIT
        if (name.equals("quitButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.exit();
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // Stats
        if (name.equals("statsButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(stats);

                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }



        // Play
        if (name.equals("playButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(gym);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }
        // Back
        if (name.equals("backButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(previousScreen);

                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // MAp
        if (name.equals("map")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(map);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // Gym
        if (name.equals("gym")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(gym);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // Room
        if (name.equals("room")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(room);

                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // Options
        if (name.equals("optionButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(options);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // MainMenu
        if (name.equals("mainMenuButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(menu);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // Squat
        if (name.equals("squatButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 890 / mapCoorinateCorrector,
                            125 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.squat, 865, 125);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // SquatTechnic
        if (name.equals("technicSquatButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 890 / mapCoorinateCorrector,
                            175 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.squatTechnic, 865, 150);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // chouseSquatMenu
        if (name.equals("chouseSquatMenu")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpWindow(chouseSquatMenu);

                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // Bench
        if (name.equals("benchButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 1300 / mapCoorinateCorrector,
                            150 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.bench, 1350, 35);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // BenchTechnic
        if (name.equals("technicBenchButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 890 / mapCoorinateCorrector,
                            175 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.benchTechnic, 865, 150);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // Deadlift
        if (name.equals("deadliftButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 890 / mapCoorinateCorrector,
                            50 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.deadlift, 865, 30);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // DeadliftTechnic
        if (name.equals("technicDeadliftButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 890 / mapCoorinateCorrector,
                            50 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.deadliftTechnic, 865, 150);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // GripButton
        if (name.equals("gripButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 1000 / mapCoorinateCorrector,
                            350 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.gripWorkout, 1000, 350);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // Arch
        if (name.equals("archButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 1200 / mapCoorinateCorrector,
                            350 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.archWorkout, 1200, 350);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // WORK
        if (name.equals("workButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(work);
                    setUpWindow(workMenu);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // WORK
        if (name.equals("workProgressButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    player.setPlayersAction(Player.PlayerCondition.working, 0, 0);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // Sleeping
        if (name.equals("sleepButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 1000 / mapCoorinateCorrector,
                            300 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.sleeping, 1920 / 2, -1);

                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // OpenWindow
        if (name.equals("openWindow")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpWindow(refregirator);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }
        // Food
        if (name.equals("potato")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    player.energy.addProgress(0.25f);
                    player.food.addProgress(0.25f);
                    player.health.addProgress(0.25f);
                    System.out.print(" Feed");
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }
            };
        }

        // CloseWindow
        if (name.equals("closeWindow")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    currentWindow = none;
                    windowGroup.clear();
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        if (name.equals("girlButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    path = map2d.findPath((int) player.getX() / mapCoorinateCorrector,
                            (int) player.getY() / mapCoorinateCorrector, 955 / mapCoorinateCorrector,
                            430 / mapCoorinateCorrector);
                    player.setPlayersAction(Player.PlayerCondition.talkToArmGirl, 955,430);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        return listener;
    }
}
