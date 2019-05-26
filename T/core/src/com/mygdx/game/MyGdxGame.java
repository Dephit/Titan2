package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
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
import static com.mygdx.game.MyGdxGame.Windows.choseSquatWindow;
import static com.mygdx.game.MyGdxGame.Windows.none;
import static com.mygdx.game.MyGdxGame.Windows.refregirator;
import static com.mygdx.game.MyGdxGame.Windows.workMenu;
import static com.mygdx.game.MyMethods.createProceduralPixmap;
import static com.mygdx.game.MyMethods.getJson;
import static com.mygdx.game.MyMethods.getPath;
import static com.mygdx.game.MyMethods.getTextButtonStyleFromFile;
import static com.mygdx.game.MyMethods.textDrawing;

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

    private static Array<Objects> playObjects = new Array<Objects>();
    private static Array<Objects> allObjects = new Array<Objects>();

    private ArrayList<TextButton> buttonsArr = new ArrayList<TextButton>();
    private static Group screenButtons = new Group();

    private Grid2d map2d;
    private double[][] mapArr;
    private Group objectDrawOrderGroup = new Group(), hudGroup = new Group(), windowGroup = new Group();

    public static Locale locale = null;
    private ArrayList<ButtonData> buttonDataArrayList;
    private String language;
    private Window windowActor;
    private AssetManager manager;


    public enum Screens {
        gym, room, map, work, shop, menu, options, stats
    }

    public enum Windows {
        none, refregirator, choseSquatWindow, workMenu
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
        public ArrayList<String[]> screens = new ArrayList<String[]>(); // There are should be 5 items in an array: name, x, y, width, height
        //in same order, and all String, so you should consider parsing to int
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
        choseLanguage("ru");
        manager = new AssetManager();
        stage = new Stage(new StretchViewport(1920, 1080));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        createButtons();
        player = new Player();
        setUpRoom(menu);
        //TODO Try to make it simple
        for (StatBar stat : player.stats) {
            hudGroup.addActor(stat);
        }
        stage.addActor(hudGroup);
        stage.addActor(windowGroup);

        //This is for debugging, delete it after release or don't
        pix2 = createProceduralPixmap(1, 1, 1, 0, 0);
        tex2 = new Texture(pix2);
        pix3 = createProceduralPixmap(1, 1, 0, 1, 0);
        tex3 = new Texture(pix3);
        pix4 = createProceduralPixmap(1, 1, 0, 0, 1);
        tex4 = new Texture(pix4);

        Gdx.input.setInputProcessor(stage);
    }

    private void choseLanguage(String language) {
        this.language = language;
        if (this.language.equals("ru"))
            locale = new Locale("ru", "RU");
        else
            locale = new Locale("es", "ES");
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
        file.writeString("", false);
        String score = json.toJson(coordinates);
        file.writeString(score, true);
    }


    private void createButtons() {
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal(getPath() + "screens/buttons/buttons.atlas"));
        skin.addRegions(buttonAtlas);

        String buttonList = getJson("screens/buttons/buttons.json");
        String textList = getJson("screens/buttons/buttonText/buttonText" + locale.getCountry() + ".json");
        //try {
        buttonDataArrayList = json.fromJson(ArrayList.class, buttonList);
       //}catch (SerializationException e){};

        final ArrayList<ButtonText> buttonTextArrayList = json.fromJson(ArrayList.class, textList);
        for (ButtonData buttonData : buttonDataArrayList) {
            try {
                final TextButton textButton = new TextButton("", getTextButtonStyleFromFile(skin, buttonData.name));
                textButton.setName(buttonData.name);
                textButton.addListener(getListener(textButton.getName()));

                for (ButtonText buttonText : buttonTextArrayList) {
                //TODO Add possibility to add colors from json
                //TODO Add possibility to change font's color from json
                //TODO Add possibility to change font's color from json
                //TODO Add possibility to change font's down color from json
                    if (buttonText.buttonName.equals(textButton.getName())) {
                        textButton.setText(buttonText.text);
                        textButton.getLabel().setFontScale(0.9f);
                        break;
                    }
                }
                buttonsArr.add(textButton);
            }catch (GdxRuntimeException ignored){}
        }
        skin.dispose();
    }

    private void setUpRoom(Screens string) {
        previousScreen = currentScreen;
        currentScreen = string;
        setUpWindow(none);

        clearStage();
        //TODO Make asynk loader
     //   manager.load(getPath()+"screens/" + string + "/" + string + ".png", Texture.class);
        //System.out.println(manager.);
       // if(manager.update())
       // if(manager.isFinished())
        //    background = manager.get(getPath()+"screens/" + string + "/" + string + ".png", Texture.class);
      //  else
            background = MyMethods.LoadImg("screens/" + string + "/" + string + ".png");

        try {
            createObjects(string.toString());
        } catch (GdxRuntimeException ignored) { }

        loadButtons(string.toString(), screenButtons);

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

    private void loadButtons(String string, Group group) {
       // String buttonList = getJson("screens/" + string + "/buttons/" + "buttons.json");
        for (final ButtonData buttonData : buttonDataArrayList) {
            for (TextButton textButton : buttonsArr) {
                if (buttonData.name.equals(textButton.getName()) ) {
                    //In perfect world make a goddamn class, you stupid idiot
                    //The indexes below means 2: x, 3: y, 4: width, 5: height, i think it's obvious, P.S. 0 and 1 for screen name and img name respectively
                    for (String[] screen : buttonData.screens) {
                        if (screen[0].equals(string)) {
                            textButton.setBounds(Integer.valueOf(screen[2]), Integer.valueOf(screen[3]),
                                    Integer.valueOf(screen[4]), Integer.valueOf(screen[5]));
                            group.addActor(textButton);
                            break;
                        }
                    }

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
        windowGroup.clear();
        if (window != none) {
            if(windowActor == null)
                windowActor = new Window(1920 / 2 - 750f, 1080 / 2 - 350f, 1500, 700, currentWindow.toString());

            for (final ButtonData buttonData : buttonDataArrayList) {
                if(buttonData.name.equals(currentWindow.toString())) {
                    windowActor.set(Integer.valueOf(buttonData.screens.get(0)[2]), Integer.valueOf(buttonData.screens.get(0)[3]),
                            Integer.valueOf(buttonData.screens.get(0)[4]), Integer.valueOf(buttonData.screens.get(0)[5]), currentWindow.toString());
                    break;
                }
            }
            loadButtons(currentWindow.toString(), windowActor.thisGroup);
            windowGroup.addActor(windowActor);
            windowGroup.addActor(windowActor.thisGroup);
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

        drawOrder();
        stage.getBatch().end();

        stage.draw();

        if (currentWindow == none)
            stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();

        //showPath(); // Debug for showing cell which are walkable or not
        // textDrawing( stage.getBatch(),1+"",1825,990,1.5f, Color.WHITE);
        stage.getBatch().end();
    }

    private void showPath() {
        for(int i=0;i<mapSize;i++) {
            for (int j = 0; j < mapSize * 4; j++) {
                if (mapArr[i][j] == 0)
                    stage.getBatch().draw(tex3, j * mapCoorinateCorrector, i * mapCoorinateCorrector, 8, 8);
                else
                    stage.getBatch().draw(tex4, j * mapCoorinateCorrector, i * mapCoorinateCorrector, 8, 8);
                    textDrawing(stage.getBatch(),  i + " "  + j,j * mapCoorinateCorrector, i * mapCoorinateCorrector,0.6f, Color.WHITE);
            }
            stage.getBatch().draw(tex2, (int) player.getX() / mapCoorinateCorrector, (int) player.getY() / mapCoorinateCorrector, 8, 8);
            try {
                for (Grid2d.MapNode mapNode : path) {
                    stage.getBatch().draw(tex2, mapNode.x * mapCoorinateCorrector, mapNode.y * mapCoorinateCorrector, 8, 8);
                }
            } catch (NullPointerException ignored) {
            }
        }
    }

    private void keyPressing() {
        if (currentScreen == menu){
            if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
                setUpRoom(gym);
        }
    }

    private void drawOrder() {

        for (int j = 0; j < objectDrawOrderGroup.getChildren().size; j++) {
            for (int i = 0; i < objectDrawOrderGroup.getChildren().size - 1; i++) {
                if (objectDrawOrderGroup.getChildren().get(i).getY() < objectDrawOrderGroup.getChildren().get(i + 1).getY())
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

            //if(mapArr[(int) touchPos.y / mapCoorinateCorrector][(int) touchPos.x / mapCoorinateCorrector] != -1)

            //else mapArr[(int) touchPos.y / mapCoorinateCorrector][(int) touchPos.x / mapCoorinateCorrector] = -1;

            try {
                if(Gdx.input.isKeyPressed(Input.Keys.Q))
                    mapArr[(int) touchPos.y / mapCoorinateCorrector][(int) touchPos.x / mapCoorinateCorrector] = 0;
                else if(Gdx.input.isKeyPressed(Input.Keys.W))
                    mapArr[(int) touchPos.y / mapCoorinateCorrector][(int) touchPos.x / mapCoorinateCorrector] = -1;
                else if(Gdx.input.isKeyPressed(Input.Keys.S)) saveRoomMap();
                else if (mapArr[(int) touchPos.y / mapCoorinateCorrector][(int) touchPos.x / mapCoorinateCorrector] != -1) {
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

        // choseSquatMenu
        if (name.equals("choseSquatMenu")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpWindow(choseSquatWindow);
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
                    setUpWindow(none);
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
