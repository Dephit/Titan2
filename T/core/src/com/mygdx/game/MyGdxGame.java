package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Player.PlayerCondition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import static com.mygdx.game.MyGdxGame.Screens.gym;
import static com.mygdx.game.MyGdxGame.Screens.map;
import static com.mygdx.game.MyGdxGame.Screens.menu;
import static com.mygdx.game.MyGdxGame.Screens.options;
import static com.mygdx.game.MyGdxGame.Screens.room;
import static com.mygdx.game.MyGdxGame.Screens.shop;
import static com.mygdx.game.MyGdxGame.Screens.stats;
import static com.mygdx.game.MyGdxGame.Screens.work;
import static com.mygdx.game.MyGdxGame.Windows.choseSquatWindow;
import static com.mygdx.game.MyGdxGame.Windows.none;
import static com.mygdx.game.MyGdxGame.Windows.nuggetsMenu;
import static com.mygdx.game.MyGdxGame.Windows.potatoMenu;
import static com.mygdx.game.MyGdxGame.Windows.pullUpsWin;
import static com.mygdx.game.MyGdxGame.Windows.refrigerator;
import static com.mygdx.game.MyGdxGame.Windows.workMenu;
import static com.mygdx.game.MyMethods.createProceduralPixmap;
import static com.mygdx.game.MyMethods.getJson;
import static com.mygdx.game.MyMethods.getPath;
import static com.mygdx.game.MyMethods.getTextButtonStyleFromFile;
import static com.mygdx.game.MyMethods.textDrawing;
import static com.mygdx.game.Player.PlayerCondition.archWorkout;
import static com.mygdx.game.Player.PlayerCondition.bench;
import static com.mygdx.game.Player.PlayerCondition.benchTechnic;
import static com.mygdx.game.Player.PlayerCondition.deadlift;
import static com.mygdx.game.Player.PlayerCondition.deadliftTechnic;
import static com.mygdx.game.Player.PlayerCondition.gripWorkout;
import static com.mygdx.game.Player.PlayerCondition.hiper;
import static com.mygdx.game.Player.PlayerCondition.legPress;
import static com.mygdx.game.Player.PlayerCondition.openRef;
import static com.mygdx.game.Player.PlayerCondition.pcSitting;
import static com.mygdx.game.Player.PlayerCondition.pullUps;
import static com.mygdx.game.Player.PlayerCondition.pushups;
import static com.mygdx.game.Player.PlayerCondition.sitting;
import static com.mygdx.game.Player.PlayerCondition.sittingRev;
import static com.mygdx.game.Player.PlayerCondition.sleeping;
import static com.mygdx.game.Player.PlayerCondition.squat;
import static com.mygdx.game.Player.PlayerCondition.squatTechnic;
import static com.mygdx.game.Player.PlayerCondition.stay;
import static com.mygdx.game.Player.PlayerCondition.talkToArmGirl;
import static com.mygdx.game.Player.PlayerCondition.talkToBicepsGuy;
import static com.mygdx.game.Player.PlayerCondition.talkToCleaner;
import static com.mygdx.game.Player.PlayerCondition.talkToCoach;
import static com.mygdx.game.Player.PlayerCondition.talkToStaff;
import static com.mygdx.game.Player.PlayerCondition.watchCam;
import static com.mygdx.game.Player.PlayerCondition.watchLoli;
import static com.mygdx.game.Player.PlayerCondition.watchShop;

public class MyGdxGame implements ApplicationListener {

    static int W;

    private OrthographicCamera camera;
    private Vector3 touchPos;
    private Player player;
    static PlayerStats playerStats;
    private static int mapSize;

    static int mapCoorinateCorrector;

    private Texture tex2, tex3, tex4;
    private Pixmap pix2, pix3, pix4;
    public static List<Grid2d.MapNode> path = new LinkedList<Grid2d.MapNode>();
    public static List<Grid2d.MapNode> path3 = new LinkedList<Grid2d.MapNode>();

    private static Array<Objects> playObjects = new Array<Objects>();
    private static Array<Objects> allObjects = new Array<Objects>();

    private ArrayList<TextButton> buttonsArr = new ArrayList<TextButton>();
    private static Group screenButtons = new Group();

    private Grid2d map2d;
    static double[][] mapArr;
    private Group objectDrawOrderGroup = new Group(), hudGroup = new Group(), windowGroup = new Group();

    public static Locale locale = null;
    private ArrayList<ButtonData> buttonDataArrayList;
    private String language;
    private Window windowActor;
    //private TextureAtlas buttonAtlas;
    private Skin skin;
    private boolean isLoaded =false;
    public static Map<String, TextToDraw> textToDrawMap = new TreeMap<>();

    public enum Screens {
        gym, map, menu, options, room, shop, stats, work

    }

    public enum Windows {
        none, refrigerator, choseSquatWindow, workMenu, pullUpsWin, potatoMenu, nuggetsMenu, buyFoodMenu
    }

    static class ButtonText {
        String buttonName;
        String text;

        public ButtonText() {
        }
    }

    static class ButtonData {
        public String name;
        public int x, y, width, height;
        ArrayList<String[]> screens = new ArrayList<String[]>(); // There are should be 5 items in an array: name, x, y, width, height
        //in same order, and all String, so you should consider parsing to int
        public ButtonData() {
        }
    }

    static class ObjectData {
        public String name;
        int x, y, width, height, rows, cols, time;
        List<int[]> animations = new ArrayList<int[]>();

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

    private AssetManager assetManager = new AssetManager();

    private IActivityRequestHandler myRequestHandler;

    public MyGdxGame(IActivityRequestHandler handler) {
        myRequestHandler = handler;
    }


    @Override
    public void create() {

        stage = new Stage(new FitViewport(1920,1080));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        FontFactory.getInstance().initialize();
        choseLanguage("ru");
        loadStuff();

        //This is for debugging, delete it before release or don't
        pix2 = createProceduralPixmap(1, 1, 1, 0, 0);
        tex2 = new Texture(pix2);
        pix3 = createProceduralPixmap(1, 1, 0, 1, 0);
        tex3 = new Texture(pix3);
        pix4 = createProceduralPixmap(1, 1, 0, 0, 1);
        tex4 = new Texture(pix4);

        Gdx.input.setInputProcessor(stage);
    }

    private void loadStuff() {
        player = new Player();
        playerStats = new PlayerStats();

        createButtons();
        setUpRoom(menu);

        //TODO Try to make it simple
        for (StatBar stat : player.stats) {
            hudGroup.addActor(stat);
        }

        stage.addActor(hudGroup);
        stage.addActor(windowGroup);
    }

    private void choseLanguage(String language) {
        this.language = language;
        if (this.language.equals("ru"))
            locale = new Locale("ru", "RU");
        else
            locale = new Locale("es", "ES");
    }

    private void createMap() {
        mapSize = 14;
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
        skin = new Skin();

        TextureAtlas buttonAtlas = /*assetManager.get(getPath() + "screens/buttons/buttons.atlas");*/new TextureAtlas(Gdx.files.internal(getPath() + "screens/buttons/buttons.atlas"));
        skin.addRegions(buttonAtlas);

        String buttonList = getJson("screens/buttons/buttons.json");
        String textList = getJson("screens/buttons/buttonText/buttonText" + locale.getCountry() + ".json");
        //try {
        buttonDataArrayList = json.fromJson(ArrayList.class, buttonList);
       //}catch (SerializationException e){};

        final ArrayList<ButtonText> buttonTextArrayList = json.fromJson(ArrayList.class, textList);
        for (ButtonData buttonData : buttonDataArrayList) {
            try {
                //TODO add an empty image to folder as a standart image
                final TextButton textButton = new TextButton("", getTextButtonStyleFromFile(skin, "map"));
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

    private void setUpRoom(Screens string){
        isLoaded = false;
        assetManager.clear();
        //setUpWindow(none);
        //clearStage();
        //TODO Make asynk loader
        // assetManager.unload(getPath() + "screens/" + previousScreen + "/" + previousScreen + ".png");
//        loading(string);
        setUpRoom2(string);

    }

    private void setUpRoom2(Screens string) {
       /* if(string == room)
            player.sizeMult = 6;
        else player.sizeMult = 5;*/
        isLoaded = true;
       // assetManager.clear();
       // manager = false;
        previousScreen = currentScreen;
        currentScreen = string;
        setUpWindow(none);

        clearStage();

        if(string == room){
            player.setPosition(1100, 300);
            player.lastWalkeblePosition.set(1100,300);
        }else   if(string == shop){
            player.setPosition(1700, 100);
            player.lastWalkeblePosition.set(1700,100);
        }

        //TODO Make asynk loader
       // assetManager.unload(getPath() + "screens/" + previousScreen + "/" + previousScreen + ".png");


       // assetManager.load(getPath() + "screens/" + string + "/" + string + ".png", Texture.class);

       /* if(background != null){
            background.dispose();
            background = null;
        }*/
        assetManager.load(getPath() + "screens/" + string + "/" + string + ".png", Texture.class);

      //  background = assetManager.get(getPath() + "screens/" +string + "/" + string + ".png", Texture.class);


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

        //textureAtlas = assetManager.get(getPath() + "screens/" + string + "/objects/objects.atlas");
        TextureAtlas textureAtlas = new TextureAtlas(getPath() + "screens/" + string + "/objects/objects.atlas");
       // assetManager.load(getPath() + "screens/" + currentScreen + "/objects/objects.atlas", TextureAtlas.class);
        final ArrayList<ObjectData> objectDataArrayList = json.fromJson(ArrayList.class, objectList);

        for (final ObjectData objectData : objectDataArrayList) {
            boolean needLoad = false;
          /*  for (Objects object : allObjects) {
                if(object.getName().equals(objectData.name)) {
                    playObjects.add(object);
                    needLoad = true;
                    break;
                }
            }*/
            if(!needLoad) {
                Objects objects = new Objects("screens/" + string + "/objects/", objectData, textureAtlas);
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

                            textButton.setStyle(getTextButtonStyleFromFile(skin, screen[1]));

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
                windowActor = new Window(0, 0, 0,0, currentWindow.toString());
            for (final ButtonData buttonData : buttonDataArrayList) {
                if(buttonData.name.equals(currentWindow.toString())) {
                    windowActor.set(Integer.valueOf(buttonData.screens.get(0)[2]), Integer.valueOf(buttonData.screens.get(0)[3]),
                            Integer.valueOf(buttonData.screens.get(0)[4]), Integer.valueOf(buttonData.screens.get(0)[5]), currentWindow.toString());
                    break;
                }
            }

            loadButtons(currentWindow.toString(), windowActor.thisGroup);
          //windowActor.makeList();
            if(window == refrigerator)
                windowActor.ordrIt();
                   //windowActor.refStuff();
            windowGroup.addActor(windowActor);
            windowGroup.addActor(windowActor.thisGroup);
        }
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
        camera.update();
    }

    @Override
    public void render() {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        keyPressing();
        touches();
        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.getBatch().begin();
        //if(Gdx.input.isKeyPressed(Input.Keys.L))
        //System.out.println(assetManager.getProgress());

        if(assetManager.isFinished()) {
            //loading();
            loading(currentScreen);
            //if(!isLoaded)
             //   setUpRoom2(screen);
            if(background != null)
                stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());

        } else {

            assetManager.update();
            textDrawing(stage.getBatch(), "Загрузка: " + assetManager.getProgress(), 500, 500, 2, Color.CORAL);
        }


        drawOrder();

        stage.getBatch().end();

        if(assetManager.isFinished()){
            stage.draw();
        }

        if (currentWindow == none)
            stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        hideStuff();
/*        for (Map.Entry<String, TextToDraw> textToDrawEntry : textToDrawMap.entrySet()) {
            if(textToDrawEntry.getKey().equals(currentScreen.toString()) || textToDrawEntry.getKey().equals(currentWindow.toString())){
                textDrawing(stage.getBatch(), textToDrawEntry.getValue().str, textToDrawEntry.getValue().x, textToDrawEntry.getValue().y,textToDrawEntry.getValue().scale,textToDrawEntry.getValue().color);
                System.out.println("yes");
            }
        }*/
        //showPath(); // Debug for showing cell which are walkable
        // textDrawing( stage.getBatch(),1+"",1825,990,1.5f, Color.WHITE);
        stage.getBatch().end();

        waitForIt();
    }

    private void waitForIt() {
        if(player.getPlayerCondition().equals(openRef)){
            player.setPlayersAction(stay,0,0);
            player.changePlayerCondition();
            setUpWindow(refrigerator);
        }
    }

    private void loading(Screens string) {
        background = assetManager.get(getPath() + "screens/" +string + "/" + string + ".png", Texture.class);

        //assetManager.load(getPath() + "screens/" + currentScreen + "/objects/objects.atlas", TextureAtlas.class);
        //background = assetManager.get(getPath() + "screens/" +string + "/" + string + ".png", Texture.class);
      //  if(background == null )
     //   assetManager.load(getPath() + "screens/" + currentScreen + "/" + currentScreen + ".png", Texture.class);
              // assetManager.load(getPath() + "screens/buttons/buttons.atlas", TextureAtlas.class);
        //assetManager.load(getPath() + "screens/" + currentScreen + "/objects/objects.atlas", TextureAtlas.class);

       // textureAtlas = assetManager.get(getPath() + "screens/" + string + "/objects/objects.atlas",  TextureAtlas.class);
    }

    private void hideStuff() {
        for (Objects playObject : playObjects) {
            hide(playObject,"legpress", new PlayerCondition[]{legPress});
            hide(playObject, "rack", new PlayerCondition[]{squat});
            hide(playObject, "deadlift", new PlayerCondition[]{deadlift});
            hide(playObject, "bench", new PlayerCondition[]{bench});
            hide(playObject, "pullups", new PlayerCondition[]{pullUps, pushups});
        }
    }

    private void hide(Objects playObject, String objectName, PlayerCondition[] condition) {
        if(playObject.getName().equals(objectName)) {
            int i = 0;
            for (PlayerCondition playerCondition : condition) {
                if (player.getPlayerCondition() == playerCondition)
                    i++;
            }
            if (i > 0)
                playObject.setCertainFrame(true);
            else
                playObject.setCertainFrame(false);
        }
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
        } else  if (currentScreen == gym){
            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                player.setPosition(player.getX(), player.getY() + 5);
                player.setPlayerCondition(PlayerCondition.up);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.setPosition(player.getX(), player.getY() - 5);
                player.setPlayerCondition(PlayerCondition.down);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.setPosition(player.getX() - 5, player.getY());
                player.setPlayerCondition(PlayerCondition.left);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.setPosition(player.getX() + 5, player.getY());
                player.setPlayerCondition(PlayerCondition.right);
            }
        }
    }

    private void drawOrder() {
        for (int j = 0; j < objectDrawOrderGroup.getChildren().size; j++) {
            for (int i = 0; i < objectDrawOrderGroup.getChildren().size - 1; i++) {
                if (objectDrawOrderGroup.getChildren().get(i).getY() < objectDrawOrderGroup.getChildren().get(i + 1).getY())
                    objectDrawOrderGroup.getChildren().swap(i, i + 1);

                if (objectDrawOrderGroup.getChildren().get(i).getName().equals("player")
                        && (player.getPlayerCondition().equals(PlayerCondition.bench) ||
                            player.getPlayerCondition().equals(PlayerCondition.pullUps) ||
                            player.getPlayerCondition().equals(legPress) ||
                        player.getPlayerCondition().equals(sitting) ||
                        player.getPlayerCondition().equals(sittingRev) ||
                        player.getPlayerCondition().equals(hiper) ||
                        player.getPlayerCondition().equals(pushups))||
                        player.getPlayerCondition().equals(pcSitting))
                    objectDrawOrderGroup.getChildren().swap(i, objectDrawOrderGroup.getChildren().size - 1);
            }
        }
        stage.getActors().swap(stage.getActors().indexOf(hudGroup, true), stage.getActors().indexOf(windowGroup, true));
        stage.getActors().swap(stage.getActors().indexOf(windowGroup, true), stage.getActors().size - 1);
    }

    private void touches() {
        if (Gdx.input.isTouched()) {
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            try {
                if(Gdx.input.isKeyPressed(Input.Keys.Q))
                    mapArr[(int) touchPos.y / mapCoorinateCorrector][(int) touchPos.x / mapCoorinateCorrector] = 0;
                else if(Gdx.input.isKeyPressed(Input.Keys.W))
                    mapArr[(int) touchPos.y / mapCoorinateCorrector][(int) touchPos.x / mapCoorinateCorrector] = -1;
                else if(Gdx.input.isKeyPressed(Input.Keys.S))
                    saveRoomMap();
                else if(currentWindow == none)
                    if (mapArr[(int) touchPos.y / mapCoorinateCorrector][(int) touchPos.x / mapCoorinateCorrector] != -1) {
                        setPath((int)touchPos.x, (int)touchPos.y, 0, 0, stay);
                    }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }

    private void setPath(int xGoal, int yGoal, int xDestination, int yDestination, PlayerCondition playerCondition) {
        int pX = (int) player.getX() / mapCoorinateCorrector, pY = (int) player.getY() / mapCoorinateCorrector;

        if (path != null)
            if (path.size() > 0)
                if (MyGdxGame.path.get(0).x != (int) player.getX() / MyGdxGame.mapCoorinateCorrector
                        || MyGdxGame.path.get(0).y != (int) player.getY() / MyGdxGame.mapCoorinateCorrector) {
                    player.lastWalkeblePosition.set(player.getX(), player.getY());
                }

        if (xGoal / mapCoorinateCorrector != map2d.xGoal || yGoal / mapCoorinateCorrector != map2d.yGoal ){

            player.setPosition(player.lastWalkeblePosition.x, player.lastWalkeblePosition.y);
            player.ceilPos();

            path = map2d.findPath(
                    (int) player.getX() / mapCoorinateCorrector,
                    (int) player.getY() / mapCoorinateCorrector,
                    xGoal / mapCoorinateCorrector,
                    yGoal / mapCoorinateCorrector);
        }

        player.setPlayersAction(playerCondition, xDestination, yDestination);
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

        if (name.equals("changeViewportToStretch")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                   stage.setViewport(new StretchViewport(1920,1080));
                    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        if (name.equals("changeViewportToFit")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    stage.setViewport(new FitViewport(1920,1080));
                    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true);
                    camera.setToOrtho(false, 1920, 1080);
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

        if (name.equals("shop")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpRoom(shop);
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
                    setPath(890 , 125, 865, 125, squat);
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
                    setPath(890, 175, 865, 150, squatTechnic);
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

        // choseSquatMenu
        if (name.equals("pullUpsOpenWin")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpWindow(pullUpsWin);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // MainMenu
        if (name.equals("watchAlc") || name.equals("watchAlc2") || name.equals("watchAlc3") || name.equals("watchAlc4")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(name.equals("watchAlc"))
                        setPath(700, 400, 700, 400, watchShop);
                    else if(name.equals("watchAlc2"))
                        setPath(900, 400, 900, 400, watchShop);
                    else if(name.equals("watchAlc3"))
                        setPath(1100, 400, 1100, 400, watchShop);
                    else
                        setPath(1630, 325, 1630, 325, watchShop);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        if (name.equals("watchLoli")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(320, 300, 320, 300, watchLoli);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        if (name.equals("watchCam")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(400, 400, 400, 400, watchCam);
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
                    setPath(1337, 160, 1353, 75, bench);
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
                    setPath(1300, 150, 1337, 75, benchTechnic);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }
        //PullUps
        if (name.equals("pullUpsButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(800, 430, 820, 440, pullUps);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        //PullUps
        if (name.equals("pushupsButtom")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(800, 430, 818, 440, pushups);
                    setUpWindow(none);
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
                    setPath(890, 100, 865, 70, deadlift);
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
                    setPath(890, 100, 865, 70, deadliftTechnic);
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
                    setPath(1000, 350, 1000, 350, gripWorkout);
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
                    setPath(1200, 350, 1200,350,archWorkout);
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
                    player.setPlayersAction(PlayerCondition.working, 0, 0);
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
                        setPath(560, 300, 561, 367, sleeping);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // OpenWindow
        if (name.equals("refButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath( 860, 560,  860, 500, openRef);
                    // setUpWindow(refrigerator);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // OpenWindow
        if (name.equals("buyMenuBut")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath( 1000, 280,  1000, 280, openRef);
                    // setUpWindow(refrigerator);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        // FoodpotatoMenu
        if (name.equals("agreePotatoButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(currentScreen.equals(room)) {
                        player.energy.addProgress(0.25f);
                        player.food.addProgress(0.25f);
                        player.health.addProgress(0.25f);
                        playerStats.potatoAmount--;
                        if (playerStats.potatoAmount == 0)
                            setUpWindow(refrigerator);
                    }else {
                        playerStats.potatoAmount++;
                    }
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
            };
        }

        if (name.equals("agreeNuggetsButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(currentScreen.equals(room)) {
                        player.energy.addProgress(0.15f);
                        player.food.addProgress(0.50f);
                        player.health.addProgress(0.5f);
                        playerStats.nuggetsAmount--;
                        if (playerStats.nuggetsAmount == 0)
                            setUpWindow(refrigerator);
                    }else {
                        playerStats.nuggetsAmount++;
                    }
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
            };
        }

        if (name.equals("declineFood")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(currentScreen.equals(room)) {
                        setUpWindow(refrigerator);
                    }else setUpWindow(refrigerator);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
            };
        }

        if (name.equals("potato")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                  setUpWindow(potatoMenu);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
            };
        }

        if (name.equals("nuggets")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setUpWindow(nuggetsMenu);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
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

        // CloseWindow
        if (name.equals("showAdBut")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(myRequestHandler.isAdShown())
                        myRequestHandler.showAds(false);
                    else {
                        myRequestHandler.signOut();
                        myRequestHandler.showAds(true);
                    }

                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        if (name.equals("sighOut")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        myRequestHandler.signOut();
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };
        }

        //TalkToGirl

        if (name.equals("girlButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(1025, 450, 0, 0, talkToArmGirl);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }
        //TalkToCoach

        if (name.equals("talkToCleanerBut")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(1440, 420, 0, 0, talkToCleaner);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }

        if (name.equals("talkToStaffBut")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(392, 450, 0, 0, talkToStaff);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }

        if (name.equals("talkToCoach")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(350, 50, 0, 0, talkToCoach);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }
        //TalkToGuy
        if (name.equals("talkToBicGuy")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(400,300, 0,0, talkToBicepsGuy);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }
        //LegPress
        if (name.equals("legPressButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(1550,160, 1695,65, legPress);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }
        //sitting
        if (name.equals("sittingButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(260,240, 210,280, sitting);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }

        if (name.equals("sittingRevButton")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(1680,260, 1720,280, sittingRev);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }

        if (name.equals("pcSittingBut")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(900,260, 905,187, pcSitting);
                    //setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }

        if (name.equals("hiperBut")) {
            listener = new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    setPath(565,160, 555,50, hiper);
                    setUpWindow(none);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            };
        }
        return listener;
    }
}


