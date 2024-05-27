package catchorwaste;

import catchorwaste.controller.GPIOController;
import catchorwaste.controller.PunktesystemController;
import catchorwaste.controller.TimerController;
import catchorwaste.controller.entities.CartController;
import catchorwaste.controller.entities.FallingObjectController;
import catchorwaste.controller.screens.EndScreenController;
import catchorwaste.controller.screens.HighScoreController;
import catchorwaste.controller.screens.NameGeneratorController;
import catchorwaste.controller.screens.NewPlayerController;
import catchorwaste.controller.screens.SettingsController;
import catchorwaste.controller.screens.StartScreenController;

import catchorwaste.model.PunktesystemModel;
import catchorwaste.model.entities.FallingObjectModel;
import catchorwaste.model.screens.*;
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
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static catchorwaste.controller.entities.PlayerController.boundaries;
import static catchorwaste.controller.entities.PlayerController.catchObject;
import static catchorwaste.controller.entities.PlayerController.movePlayer;
import static catchorwaste.controller.TimerController.initTimer;
import static catchorwaste.controller.TimerController.startTimer;

import static catchorwaste.model.entities.CartModel.setGate;

import static catchorwaste.model.variables.Constants.HOUSE1_X;
import static catchorwaste.model.variables.Constants.HOUSE2_X;
import static catchorwaste.model.variables.Constants.HOUSE3_X;
import static catchorwaste.model.variables.Constants.HOUSE4_X;
import static catchorwaste.model.variables.Constants.HOUSE_Y;
import static catchorwaste.model.variables.Constants.REPARIEREN_X;
import static catchorwaste.model.variables.Constants.RECYCLE_X;
import static catchorwaste.model.variables.Constants.WORKSTATION_RIGHT_Y;
import static catchorwaste.model.variables.Constants.MARKT_X;
import static catchorwaste.model.variables.Constants.STREET_HEIGHT;

import static catchorwaste.view.entities.PlayerView.isAtStreetEnd;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKeyDown;



public class CatchOrWasteApp extends GameApplication implements TimerController.TimerListener {

    public static Map<String, Image> imageMap;
    public static Map<String, ArrayList<String>> languageMap;
    public static String[] languages = {"german", "english", "french"};

    private NewPlayerController newPlayerController;
    private StartScreenController startScreenController;
    private EndScreenController endScreenController;
    private SettingsController settingsController;
    private FallingObjectController fallingObjectController;
    private NameGeneratorController nameGeneratorController;
    private HighScoreController highScoreController;
    private PunktesystemController punkteSystemController;

    private CartController cartController;
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
            controller.initControllers(startScreenController, newPlayerController);
            controller.initActions(this::leftControl, this::rightControl,this::acceptControl,
                    this::upControl, this::downControl, this::btnLeftControl, this::btnRightControl);
            controller.setup();
        }

        onKeyDown(KeyCode.SPACE, "Start Game",  wrapMethod.apply(this::acceptControl));

        FXGL.getInput().addAction(new UserAction("Move Right") {

            @Override
            protected void onActionBegin() {
                rightControl();
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
                leftControl();
            }

            @Override
            protected void onAction() {
                if(gameState.equals(GameState.GAME)) {
                    movePlayer(false, getGameWorld());
                }
            }
        }, KeyCode.LEFT);


        onKeyDown(KeyCode.UP, "up", wrapMethod.apply(this::upControl));

        onKeyDown(KeyCode.DOWN, "down", wrapMethod.apply(this::downControl));

        onKeyDown(KeyCode.DIGIT1, "1", wrapMethod.apply(this::btnRightControl));

        onKeyDown(KeyCode.DIGIT2, "2", wrapMethod.apply(this::btnLeftControl));


    }

    @Override
    protected void initGame() {
        //load resources
        imageMap = loadImages();
        textMap = loadText();
        languageMap = textMap.get("german");

        initControllers();

        //register eventHandlers such as collison handlers
        FXGL.onCollision(EntityType.CART, EntityType.WORKSTATION,
                (cart, workstation) -> cartController.onWorkstationCollision(cart,workstation));

        TimerController.setTimerListener(this);

        //register Entity Factory
        getGameWorld().addEntityFactory(new EntityFactory());

        //start music
        if(System.getProperty("os.name").contains("Windows")){
            playBackgroundMusic("src/main/resources/sounds/music.mp3");
        }else{
            playBackgroundMusic("/home/pi4j/deploy/music.mp3");
        }

        getGameScene().setCursorInvisible();

        //call first Screen
        //callNameGenerator();
        callHighScore();
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
        callScreen(GameState.STARTSCREEN, () -> startScreenController.initStartScreen());
    }

    private void startGame(){
        removeEverything();
        gameState = GameState.GAME;

        spawnEnvironment();
        punkteSystemController.initPunktesystem();
        initTimer();

        setGate(true);

        fallingObjectController.setGameStartTime(System.currentTimeMillis());
        startTimer();
    }

    private void callNameGenerator(){
        callScreen(GameState.NAMEGENERATOR, () -> nameGeneratorController.initNameGenerator());
    }

    private void callSettings(){
        callScreen(GameState.SETTINGS, ()->settingsController.initSettings());
    }

    private void startTutorial(){
        //TODO implement Tutorial call here
    }

    private void callEndscreen(){
        highScoreController.addHighScore();
        callScreen(GameState.ENDSCREEN, () -> endScreenController.initEndscreen());
    }

    private void callHighScore(){
        highScoreController.readHighScore();
        callScreen(GameState.HIGHSCORE, () -> highScoreController.initHighScoreView());
    }
    private void restartGame(){
        resetControllers();
        callNewPlayerScreen();

    }

    private void callNewPlayerScreen(){
        callScreen(GameState.NEWPLAYER, ()-> newPlayerController.initNewPlayerScreen());
    }

    private void callScreen(GameState gamestate, Runnable runnable){
        removeEverything();
        gameState = gamestate;
        runnable.run();
    }




    //assistance methods
    public Map<String, Image> loadImages() {

        String path;

        if(System.getProperty("os.name").contains("Windows")){
            path = "src/main/resources/assets/textures";
        }else{
            path = "/home/pi4j/deploy/assets/textures";
        }


        Map<String, Image> map = new HashMap<>();
        var directories = new File(path).listFiles();

        assert directories != null;
        for (File subDir: directories){
            if(subDir.isDirectory()){
                var filesInDir = new ArrayList<String>();
                for (File file: subDir.listFiles()){
                    filesInDir.add(file.getName().replace(".png",""));
                }
                addToMap(subDir.getName(), filesInDir.toArray(String[]::new), map);
            }
        }
        return map;
    }

    public Map<String, Map<String, ArrayList<String>>> loadText(){

        Map<String, Map<String, ArrayList<String>>> returnMap = new HashMap<>();

        String[] fileNames = {"german", "english", "french"};
        for (int i=0; i<fileNames.length; i++) {
            File file = null;
            if(System.getProperty("os.name").contains("Windows")){
                file = new File("src/main/resources/config/language_files/"+fileNames[i]+".json");
            }else{
                file = new File("/home/pi4j/deploy/"+fileNames[i]+".json");
            }
            Map<String, ArrayList<String>> map = new HashMap<>();
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(
                        new FileReader(file));

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

    public void fallingObjectOnUpdate(GameWorld gameWorld) {
        fallingObjectController.spawnObjects();
        fallingObjectController.dropObjects();
        fallingObjectController.stickToPlayer();
    }

    public void cartOnUpdate(GameWorld gameWorld) {
        cartController.cartMovement(gameWorld);
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

    public void initControllers(){
        var newPlayerModel = new NewPlayerModel();
        newPlayerController = new NewPlayerController(newPlayerModel);

        var startScreenModel = new StartScreenModel();
        startScreenController = new StartScreenController(startScreenModel);

        endScreenController = new EndScreenController();

        var settingsModel = new SettingsModel();
        settingsController = new SettingsController(settingsModel);

        var punkteSystemModel = new PunktesystemModel();
        punkteSystemController = new PunktesystemController(punkteSystemModel);

        var fallingObjectsModel = new FallingObjectModel();
        fallingObjectController = new FallingObjectController(fallingObjectsModel,
                settingsController, punkteSystemController);

        var nameGeneratorModel = new NameGeneratorModel();
        nameGeneratorController = new NameGeneratorController(nameGeneratorModel);

        var highScoreModel = new HighScoreModel();
        highScoreController = new HighScoreController(highScoreModel);

        cartController = new CartController(punkteSystemController);

    }

    public void resetControllers(){
        var newPlayerModel = new NewPlayerModel();
        newPlayerController = new NewPlayerController(newPlayerModel);

        var startScreenModel = new StartScreenModel();
        startScreenController = new StartScreenController(startScreenModel);

        var punkteSystemModel = new PunktesystemModel();
        punkteSystemController = new PunktesystemController(punkteSystemModel);

        var fallingObjectsModel = new FallingObjectModel();
        fallingObjectController = new FallingObjectController(fallingObjectsModel,
                settingsController, punkteSystemController);

        var nameGeneratorModel = new NameGeneratorModel();
        nameGeneratorController = new NameGeneratorController(nameGeneratorModel);

        var highScoreModel = new HighScoreModel();
        highScoreController = new HighScoreController(highScoreModel);

        cartController = new CartController(punkteSystemController);

    }

    private void leftControl(){
        if(gameState.equals(GameState.STARTSCREEN)) {
            startScreenController.changeSelectedOption(startScreenController.getOption()-1);
        } else if (gameState.equals(GameState.NAMEGENERATOR)) {
            nameGeneratorController.setActiveLane(nameGeneratorController.getActiveLane()-1);
        } else if(gameState.equals(GameState.NEWPLAYER)){
            newPlayerController.updateChosenLabel(newPlayerController.getChosenLabel()-1);
        } else if (gameState.equals(GameState.SETTINGS) && settingsController.getSelectedLine()<4) {
            settingsController.changeSelectedColumn(settingsController.getSelectedColumn()-1);
        }
    }
    private void rightControl(){
        if(gameState.equals(GameState.STARTSCREEN)) {
            startScreenController.changeSelectedOption(startScreenController.getOption()+1);
        } else if (gameState.equals(GameState.NAMEGENERATOR)) {
            nameGeneratorController.setActiveLane(nameGeneratorController.getActiveLane()+1);
        } else if(gameState.equals(GameState.NEWPLAYER)){
            newPlayerController.updateChosenLabel(newPlayerController.getChosenLabel()+1);
        }else if (gameState.equals(GameState.SETTINGS) && settingsController.getSelectedLine()<4) {
            settingsController.changeSelectedColumn(settingsController.getSelectedColumn()+1);
        }
    }

    private void upControl(){
        if (gameState.equals(GameState.NAMEGENERATOR)) {
            nameGeneratorController.changeLetter(nameGeneratorController.getLetter()-1);
        }else if(gameState.equals(GameState.SETTINGS)) {
            settingsController.changeSelectedLine(settingsController.getSelectedLine() - 1);
        }
    }

    private void downControl(){
        if (gameState.equals(GameState.NAMEGENERATOR)) {
            nameGeneratorController.changeLetter(nameGeneratorController.getLetter()+1);
        }else if(gameState.equals(GameState.SETTINGS)) {
            settingsController.changeSelectedLine(settingsController.getSelectedLine() + 1);
        }
    }

    private void acceptControl(){
        if(gameState.equals(GameState.NEWPLAYER)){
            if(newPlayerController.getChosenLabel()==1){
                callNameGenerator();
            }else{
                callStartScreen();
            }
        }else if(gameState.equals(GameState.NAMEGENERATOR)) {
            if (nameGeneratorController.getActiveLane() == 4) {
                nameGeneratorController.saveChanges();
                callStartScreen();
            }
        } else if (gameState.equals(GameState.STARTSCREEN)) {
            if(startScreenController.getOption()==1){
                startGame();
            }else{
                callSettings();
            }
        }else if(gameState.equals(GameState.ENDSCREEN)){
            callHighScore();
        }else if(gameState.equals(GameState.SETTINGS) && settingsController.getSelectedLine() == 4){
            settingsController.updateLanguage();
            if(settingsController.isTutorialSelected()){
                System.out.println("Tutorial Selected");
                startGame();
            }else{
                startGame();
            }
        }else if(gameState.equals(GameState.HIGHSCORE)){
            restartGame();
        }
    }

    private void btnRightControl(){
        if(gameState.equals(GameState.GAME)){
            setGate(true);
        }
    }

    private void btnLeftControl(){
        if(gameState.equals(GameState.GAME)){
            setGate(false);
        }
    }


    Function<Runnable, Function0<Unit>> wrapMethod = method -> () -> {
        method.run();
        return Unit.INSTANCE;
    };

}


