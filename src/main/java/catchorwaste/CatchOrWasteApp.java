package catchorwaste;

import catchorwaste.controller.GPIOController;
import catchorwaste.controller.TimerController;
import catchorwaste.controller.StartScreenController;
import catchorwaste.controller.NameGeneratorController;
import catchorwaste.controller.SettingsController;
import catchorwaste.controller.EndScreenController;
import catchorwaste.controller.NewPlayerController;

import catchorwaste.model.enums.EntityType;
import catchorwaste.model.enums.GameState;
import catchorwaste.model.factories.EntityFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;


import com.almasb.fxgl.input.UserAction;
import javafx.scene.Node;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static catchorwaste.controller.CartController.cartMovement;
import static catchorwaste.controller.CartController.onWorkstationCollision;
import static catchorwaste.controller.FallingObjectController.dropObjects;
import static catchorwaste.controller.FallingObjectController.stickToPlayer;
import static catchorwaste.controller.HighScoreController.addHighScore;
import static catchorwaste.controller.PlayerController.boundaries;
import static catchorwaste.controller.PlayerController.catchObject;
import static catchorwaste.controller.PlayerController.movePlayer;
import static catchorwaste.controller.PunktesystemController.initPunktesystem;
import static catchorwaste.controller.SettingsController.updateLanguage;
import static catchorwaste.controller.SettingsController.isTutorialSelected;
import static catchorwaste.controller.SettingsController.changeSelectedLine;
import static catchorwaste.controller.SettingsController.changeSelectedColumn;
import static catchorwaste.controller.StartScreenController.changeSelectedOption;
import static catchorwaste.controller.TimerController.initTimer;
import static catchorwaste.controller.TimerController.startTimer;

import static catchorwaste.model.CartModel.setGate;
import static catchorwaste.model.FallingObjectModel.setGameStartTime;
import static catchorwaste.model.NameGeneratorModel.getActiveLane;
import static catchorwaste.model.NameGeneratorModel.setActiveLane;
import static catchorwaste.model.NameGeneratorModel.saveChanges;
import static catchorwaste.model.NameGeneratorModel.changeLetter;
import static catchorwaste.model.NameGeneratorModel.getLetter;
import static catchorwaste.model.SettingsModel.getSelectedLine;
import static catchorwaste.model.SettingsModel.getSelectedColumn;
import static catchorwaste.model.StartScreenModel.getOption;
import static catchorwaste.model.constants.Constants.HOUSE1_X;
import static catchorwaste.model.constants.Constants.HOUSE2_X;
import static catchorwaste.model.constants.Constants.HOUSE3_X;
import static catchorwaste.model.constants.Constants.HOUSE4_X;
import static catchorwaste.model.constants.Constants.HOUSE_Y;
import static catchorwaste.model.constants.Constants.REPARIEREN_X;
import static catchorwaste.model.constants.Constants.RECYCLE_X;
import static catchorwaste.model.constants.Constants.WORKSTATION_RIGHT_Y;
import static catchorwaste.model.constants.Constants.MARKT_X;
import static catchorwaste.model.constants.Constants.STREET_HEIGHT;

import static catchorwaste.view.FallingObjectView.spawnObjects;
import static catchorwaste.view.PlayerView.isAtStreetEnd;

import static com.almasb.fxgl.dsl.FXGLForKtKt.onKeyDown;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;



public class CatchOrWasteApp extends GameApplication implements TimerController.TimerListener {

    public static Map<String, Image> imageMap;
    public static Map<String, ArrayList<String>> languageMap;
    public static String[] languages = {"german", "english", "french"};
    public static Map<String, Map<String, ArrayList<String>>> textMap;
    public static GameState gameState;

    public static void main(String[] args) {
        launch(args);
    }


    //application methods
    @Override
    protected void initSettings(GameSettings settings) {
        //settings.setFullScreenAllowed(true);
        //settings.setFullScreenFromStart(true);

        settings.setTicksPerSecond(60);
        settings.setTitle("CatchOrWaste");

    }

    @Override
    protected void initInput() {

        String osArch = System.getProperty("os.arch").toLowerCase();

        if (osArch.contains("arm") || osArch.contains("aarch64")) {
            GPIOController controller = new GPIOController();
            controller.init();

            controller.onAcceptButton(() -> {
                if (gameState.equals(GameState.STARTSCREEN)) {
                    if(getOption()==1){
                        startGame();
                    }else{
                        callSettings();
                    }
                }else if(gameState.equals(GameState.ENDSCREEN)){
                    restartGame();
                }else if(gameState.equals(GameState.SETTINGS) && getSelectedLine() == 4){
                    updateLanguage();
                    if(isTutorialSelected()){
                        System.out.println("Tutorial Selected");
                        startGame();
                    }else{
                        startGame();
                    }
                }
            });
        }

        onKeyDown(KeyCode.SPACE, "Start Game", () -> {
            if(gameState.equals(GameState.NAMEGENERATOR)) {
                if (getActiveLane() == 4) {
                    saveChanges();
                    callStartScreen();
                }
            } else if (gameState.equals(GameState.STARTSCREEN)) {
                if(getOption()==1){
                    startGame();
                }else{
                    callSettings();
                }
            }else if(gameState.equals(GameState.ENDSCREEN)){
                restartGame();
            }else if(gameState.equals(GameState.SETTINGS) && getSelectedLine() == 4){
                updateLanguage();
                if(isTutorialSelected()){
                    System.out.println("Tutorial Selected");
                    startGame();
                }else{
                    startGame();
                }
            }
            return null;
        });

        FXGL.getInput().addAction(new UserAction("Move Right") {

            @Override
            protected void onActionBegin() {
                if(gameState.equals(GameState.SETTINGS)) {
                    changeSelectedLine(getSelectedLine() + 1);
                }else if(gameState.equals(GameState.STARTSCREEN)) {
                    changeSelectedOption(getOption()+1);
                } else if (gameState.equals(GameState.NAMEGENERATOR)) {
                    setActiveLane(getActiveLane()+1);
                }
            }

            @Override
            protected void onAction() {
                if(gameState.equals(GameState.GAME)) {
                    movePlayer(true, getGameWorld());
                }
            }
        }, KeyCode.RIGHT);


        FXGL.getInput().addAction(new UserAction("Move Left") {

            @Override
            protected void onActionBegin() {
                if(gameState.equals(GameState.SETTINGS)) {
                    changeSelectedLine(getSelectedLine() - 1);
                }else if(gameState.equals(GameState.STARTSCREEN)) {
                    changeSelectedOption(getOption()-1);
                } else if (gameState.equals(GameState.NAMEGENERATOR)) {
                    setActiveLane(getActiveLane()-1);
                }
            }

            @Override
            protected void onAction() {
                if(gameState.equals(GameState.GAME)) {
                    movePlayer(false, getGameWorld());
                }
            }
        }, KeyCode.LEFT);


        onKeyDown(KeyCode.UP, "up", () -> {
            if (gameState.equals(GameState.NAMEGENERATOR)) {
                changeLetter(getLetter()-1);
            }
            return null;
        });

        onKeyDown(KeyCode.DOWN, "down", () -> {
            if (gameState.equals(GameState.NAMEGENERATOR)) {
                changeLetter(getLetter()+1);
            }
            return null;
        });

        onKeyDown(KeyCode.DIGIT1, "1", () -> {
            if(gameState.equals(GameState.GAME)){
                setGate(true);
            } else if (gameState.equals(GameState.SETTINGS) && getSelectedLine()<4) {
                changeSelectedColumn(getSelectedColumn()-1);
            }
            return null;
        });

        onKeyDown(KeyCode.DIGIT2, "2", () -> {
            if(gameState.equals(GameState.GAME)){
                setGate(false);
            } else if (gameState.equals(GameState.SETTINGS) && getSelectedLine()<4) {
                changeSelectedColumn(getSelectedColumn()+1);
            }
            return null;
        });


    }

    @Override
    protected void initGame() {
        //load resources
        imageMap = loadImages();
        textMap = loadText();
        languageMap = textMap.get("german");

        //register eventHandlers such as collison handlers
        FXGL.onCollision(EntityType.CART, EntityType.WORKSTATION,
                (cart, workstation) -> onWorkstationCollision(cart,workstation));

        TimerController.setTimerListener(this);

        //register Entity Factory
        getGameWorld().addEntityFactory(new EntityFactory());

        //start music
        playBackgroundMusic("/home/pi4j/deploy/music.mp3");

        getGameScene().setCursorInvisible();

        //start Tutorial
        //startTutorial(); uncomment this and delete next line when Tutorial is implemented
        //callNewPlayerScreen();
        callNameGenerator();
        //callStartScreen();
    }

    @Override
    protected void onUpdate(double tpf) {
        if (gameState.equals(GameState.GAME)) {
            playerOnUpdate(getGameWorld());
            cartOnUpdate(getGameWorld());
            fallingObjectOnUpdate(getGameWorld());
        }
    }



    //game cycle
    private void callStartScreen(){
        callScreen(GameState.STARTSCREEN, StartScreenController::initStartScreen);
    }

    private void startGame(){
        removeEverything();
        gameState = GameState.GAME;

        spawnEnvironment();
        initPunktesystem();
        initTimer();

        setGate(true);

        setGameStartTime(System.currentTimeMillis());
        startTimer();
    }

    private void callNameGenerator(){
        callScreen(GameState.NAMEGENERATOR, NameGeneratorController::initNameGenerator);
    }

    private void callSettings(){
        callScreen(GameState.SETTINGS, SettingsController::initSettings);
    }

    private void startTutorial(){
        //TODO implement Tutorial call here
    }

    private void callEndscreen(){
        addHighScore();
        callScreen(GameState.ENDSCREEN, EndScreenController::initEndscreen);
    }

    private void restartGame(){
        callNameGenerator();

    }

    private void callNewPlayerScreen(){
        callScreen(GameState.NEWPLAYER, NewPlayerController::initNewPlayerScreen);
    }

    private void callScreen(GameState gamestate, Runnable runnable){
        removeEverything();
        gameState = gamestate;
        runnable.run();
    }




    //assistance methods
    public Map<String, Image> loadImages() {

        Map<String, Image> map = new HashMap<>();

        var backroundsImgs = new String[]{"background_bad", "streets_left", "streets_right"};

        var cartsImgs = new String[]{
                "cart_horizontal", "cart_vertical",
                "iphone_d_cart_vertical", "iphone_f_cart_vertical", "iphone_r_cart_vertical",
                "iphone_d_cart_horizontal", "iphone_f_cart_horizontal", "iphone_r_cart_horizontal",
                "kleid_d_cart_vertical", "kleid_f_cart_vertical", "kleid_r_cart_vertical",
                "kleid_d_cart_horizontal", "kleid_f_cart_horizontal", "kleid_r_cart_horizontal",
                "lampe_d_cart_vertical", "lampe_f_cart_vertical", "lampe_r_cart_vertical",
                "lampe_d_cart_horizontal", "lampe_f_cart_horizontal", "lampe_r_cart_horizontal"};

        var fallingObjectsImgs = new String[]{
                "iphone_d", "iphone_f", "iphone_r",
                "kleid_d", "kleid_f", "kleid_r",
                "lampe_d", "lampe_f", "lampe_r"};

        var playerImgs = new String[]{
                "wegwerfpolizist_d_l_resized", "wegwerfpolizist_d_r_resized",
                "wegwerfpolizist_l_resized", "wegwerfpolizist_r_resized"};

        var structuresImgs = new String[]{
                "haus1", "haus2", "markt", "recycle", "reparieren"};

        var endScreensImgs = new String[]{
                "endScreen_1"
        };

        var startScreensImgs = new String[]{
                "startscreen", "settingsscreen", "gamescreen"
        };

        map = addToMap("backgrounds", backroundsImgs, map);
        map = addToMap("carts", cartsImgs, map);
        map = addToMap("fallingObjects", fallingObjectsImgs, map);
        map = addToMap("player", playerImgs, map);
        map = addToMap("structures", structuresImgs, map);
        map = addToMap("endScreens", endScreensImgs, map);
        map = addToMap("startScreen", startScreensImgs, map);
        return map;
    }

    public Map<String, Map<String, ArrayList<String>>> loadText(){

        Map<String, Map<String, ArrayList<String>>> returnMap = new HashMap<>();

        String[] fileNames = {"german", "english", "french"};
        for (int i=0; i<fileNames.length; i++) {
            Map<String, ArrayList<String>> map = new HashMap<>();
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(
                        new FileReader("/home/pi4j/deploy/"+fileNames[i]+".json"));

                Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
                while (fields.hasNext()){
                    Map.Entry<String, JsonNode> field = fields.next();
                    var key = field.getKey();
                    var value = field.getValue();

                    if(value.isArray()){
                        ArrayList<String> messages = new ArrayList<>();
                        for (JsonNode node : value) {
                            messages.add(node.asText());
                        }
                        map.put(key, messages);
                    }else{
                        var list = new ArrayList<String>();
                        list.add(value.asText());
                        map.put(key,list);
                    }
                }

            }catch (Exception e){
                System.out.println(e);
            }

            returnMap.put(fileNames[i], map);
        }

        return returnMap;

    }

    public Map<String, Image> addToMap(String dir, String[] names, Map<String, Image> map) {
        for (String s : names) {
            map.put(s, getAssetLoader().loadImage(dir + "/" + s + ".png"));
        }
        return map;
    }

    private void playBackgroundMusic(String musicFile) {
        try {
            Media media = new Media(new File(musicFile).toURI().toString());
              MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.5); // Lautstärke setzen, Bereich von 0.0 bis 1.0
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Musik endlos wiederholen
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Fehler beim Laden der Musikdatei: " + e.getMessage());
        }
    }

    private void spawnEnvironment(){
        //spawn backgrounds
        spawn("BACKGROUND", new SpawnData(0, 0).put("Position", 1).put("Name", "background_bad"));
        spawn("BACKGROUND", new SpawnData(0, 0).put("Position", 2).put("Name", "streets"));

        // spawn houses
        spawn("HOUSE", new SpawnData(HOUSE1_X, HOUSE_Y).put("Position", 1));
        spawn("HOUSE", new SpawnData(HOUSE2_X, HOUSE_Y).put("Position", 2));
        spawn("HOUSE", new SpawnData(HOUSE3_X, HOUSE_Y).put("Position", 1));
        spawn("HOUSE", new SpawnData(HOUSE4_X, HOUSE_Y).put("Position", 2));

        // spawn market, repaicenter & recycling
        spawn("WORKSTATION", new SpawnData(REPARIEREN_X, WORKSTATION_RIGHT_Y).put("Position", 1));
        spawn("WORKSTATION", new SpawnData(MARKT_X, WORKSTATION_RIGHT_Y).put("Position", 2));
        spawn("WORKSTATION", new SpawnData(RECYCLE_X, getAppHeight() * 0.48).put("Position", 3));

        //spawn the player from the factory
        spawn("PLAYER", (double) getAppWidth() / 2, STREET_HEIGHT);
    }

    public static void fallingObjectOnUpdate(GameWorld gameWorld) {
        spawnObjects(gameWorld);
        dropObjects(gameWorld);
        stickToPlayer(gameWorld);
    }

    public static void cartOnUpdate(GameWorld gameWorld) {
        cartMovement(gameWorld);
    }

    public static void playerOnUpdate(GameWorld gameWorld) {
        catchObject();
        boundaries(gameWorld);
        isAtStreetEnd(gameWorld);
    }

    public void removeEverything(){

        var removeEntities = new ArrayList<>(getGameWorld().getEntities());
        for (Entity entity : removeEntities) {
            getGameWorld().removeEntity(entity);
        }

        List<Node> uinodes = getGameScene().getUINodes().stream().toList();
        for (Node node: uinodes) {
            getGameScene().removeUINode(node);
        }
    }

    @Override
    public void onTimerStopped() {
        callEndscreen();
    }


}
