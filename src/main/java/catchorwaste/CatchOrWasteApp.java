package catchorwaste;

import catchorwaste.controller.GPIOController;
import catchorwaste.controller.PunktesystemController;
import catchorwaste.controller.TimerController;
import catchorwaste.controller.entities.CartController;
import catchorwaste.controller.entities.FallingObjectController;
import catchorwaste.controller.entities.PlayerController;
import catchorwaste.controller.screens.EndScreenController;
import catchorwaste.controller.screens.HighScoreController;
import catchorwaste.controller.screens.NameGeneratorController;
import catchorwaste.controller.screens.NewPlayerController;
import catchorwaste.controller.screens.SettingsController;
import catchorwaste.controller.screens.StartScreenController;
import catchorwaste.controller.screens.TutorialController;


import catchorwaste.model.PunktesystemModel;
import catchorwaste.model.components.ImageNameComponent;
import catchorwaste.model.entities.FallingObjectModel;
import catchorwaste.model.screens.HighScoreModel;
import catchorwaste.model.screens.NameGeneratorModel;
import catchorwaste.model.screens.NewPlayerModel;
import catchorwaste.model.screens.SettingsModel;
import catchorwaste.model.screens.StartScreenModel;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

import java.io.File;
import java.io.FileReader;


import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;




import java.util.function.Function;

import static catchorwaste.controller.TimerController.initTimer;
import static catchorwaste.controller.TimerController.startTimer;

import static catchorwaste.model.variables.Constants.HOUSE1_X;
import static catchorwaste.model.variables.Constants.HOUSE2_X;
import static catchorwaste.model.variables.Constants.HOUSE3_X;
import static catchorwaste.model.variables.Constants.HOUSE4_X;
import static catchorwaste.model.variables.Constants.HOUSE_Y;
import static catchorwaste.model.variables.Constants.REPARIEREN_X;
import static catchorwaste.model.variables.Constants.WORKSTATION_RIGHT_Y;
import static catchorwaste.model.variables.Constants.MARKT_X;
import static catchorwaste.model.variables.Constants.RECYCLE_X;
import static catchorwaste.model.variables.Constants.STREET_HEIGHT;
import static catchorwaste.model.entities.CartModel.setGate;
import static catchorwaste.model.variables.globalVariables.score;
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
    private TutorialController tutorialController;
    private CartController cartController;
    private PlayerController playerController;
    public static Map<String, Map<String, ArrayList<String>>> textMap;
    public static GameState gameState;

    public static void main(String[] args) {
        launch(args);
    }

    //application methods
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
        settings.setTicksPerSecond(60);
        settings.setTitle("CatchOrWaste");
    }

    @Override
    protected void initInput() {

        initControllers();

        String osArch = System.getProperty("os.arch").toLowerCase();

        if (osArch.contains("arm") || osArch.contains("aarch64")) {
            GPIOController controller = new GPIOController();
            controller.initControllers(cartController);
            controller.initActions(this::leftControl, this::rightControl, this::upControl,
                    this::downControl, this::acceptControl, this::btnLeftControl, this::btnRightControl);
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
                    playerController.movePlayer(true, getGameWorld());
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
                    playerController.movePlayer(false, getGameWorld());
                }
            }
        }, KeyCode.LEFT);


        onKeyDown(KeyCode.UP, "up", wrapMethod.apply(this::upControl));

        onKeyDown(KeyCode.DOWN, "down", wrapMethod.apply(this::downControl));

        onKeyDown(KeyCode.DIGIT2, "2", wrapMethod.apply(this::btnRightControl));

        onKeyDown(KeyCode.DIGIT1, "1", wrapMethod.apply(this::btnLeftControl));

    }

    @Override
    protected void initGame() {
        //load resources
        imageMap = loadImages();
        textMap = loadText();
        languageMap = textMap.get("german");

        //initControllers();

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
        callNameGenerator();
    }

    @Override
    protected void onUpdate(double tpf) {
        if (gameState.equals(GameState.GAME)) {
            updateBackground();
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

    private void callTutorial(){
        callScreen(GameState.TUTORIAL, () -> tutorialController.initTutorial());
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




    //assistance methodsx

    public Map<String, Image> loadImages() {

        Map<String, Image> map = new HashMap<>();

        var backroundsImgs = new String[]{"background_bad","background_bad_crop", "streets_left", "streets_right",
                "background_black","background_brown","background_yellow","background_green_1", "background_green_2"};

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
                "endscreen","endScreen_1"
        };
        var startScreenImgs = new String[]{
                "gamescreen","settingsscreen", "startscreen", "tutorial-test-1"
        };


        map = addToMap("backgrounds", backroundsImgs, map);
        map = addToMap("carts", cartsImgs, map);
        map = addToMap("fallingObjects", fallingObjectsImgs, map);
        map = addToMap("player", playerImgs, map);
        map = addToMap("structures", structuresImgs, map);
        map = addToMap("endScreens", endScreensImgs, map);
        map = addToMap("startScreen", startScreenImgs, map);
        return map;
    }


    public Map<String, Map<String, ArrayList<String>>> loadText(){

        Map<String, Map<String, ArrayList<String>>> returnMap = new HashMap<>();

        String[] fileNames = {"german", "english", "french"};
        for (int i=0; i<fileNames.length; i++) {
            File file;
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
        spawn("BACKGROUND", new SpawnData(0, 0).put("Position", 1).put("Name", "background_bad")
                .put("ImageName", "background_bad"));
        spawn("BACKGROUND", new SpawnData(0, 0).put("Position", 2).put("Name", "streets")
                .put("ImageName", "streets_left"));

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

    public void playerOnUpdate(GameWorld gameWorld) {
        playerController.catchObject();
        playerController.boundaries(gameWorld);
        playerController.isAtStreetEnd();
        playerController.isAtStreetEnd();
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

    public void updateBackground(){
        System.out.println(getGameWorld().getEntitiesByType(EntityType.BACKGROUND)
                .get(0).getComponent(ImageNameComponent.class).getImageName());
        if (score < -200 && !getGameWorld().getEntitiesByType(EntityType.BACKGROUND)
                .get(0).getComponent(ImageNameComponent.class).getImageName().equals("background_black")) {
            setBackground("background_black");
        }else if (score > -200  && score < -100 && !getGameWorld().getEntitiesByType(EntityType.BACKGROUND)
                .get(0).getComponent(ImageNameComponent.class).getImageName().equals("background_brown")) {
            setBackground("background_brown");
        } else if (score > -100 && score < 250 && !getGameWorld().getEntitiesByType(EntityType.BACKGROUND)
                .get(0).getComponent(ImageNameComponent.class).getImageName().equals("background_bad")) {
            setBackground("background_bad");
        }else if(score > 250 && score < 500 && !getGameWorld().getEntitiesByType(EntityType.BACKGROUND)
                .get(0).getComponent(ImageNameComponent.class).getImageName().equals("background_yellow")){
            setBackground("background_yellow");
        } else if (score > 500 && score <750 && !getGameWorld().getEntitiesByType(EntityType.BACKGROUND)
                .get(0).getComponent(ImageNameComponent.class).getImageName().equals("background_green_1")) {
            setBackground("background_green_1");
        }else if (score > 750 && !getGameWorld().getEntitiesByType(EntityType.BACKGROUND)
                .get(0).getComponent(ImageNameComponent.class).getImageName().equals("background_green_2")) {
            setBackground("background_green_2");
        }
    }

    public void setBackground(String name){
        getGameWorld().getEntitiesByType(EntityType.BACKGROUND).get(0).getViewComponent().clearChildren();
        getGameWorld().getEntitiesByType(EntityType.BACKGROUND).get(0).getViewComponent()
                .addChild(new ImageView(imageMap.get(name)));
        getGameWorld().getEntitiesByType(EntityType.BACKGROUND).get(0)
                .getComponent(ImageNameComponent.class).setImageName(name);
    }

    public void initControllers(){
        var startScreenModel = new StartScreenModel();
        startScreenController = new StartScreenController(startScreenModel);

        endScreenController = new EndScreenController();

        var settingsModel = new SettingsModel();
        settingsController = new SettingsController(settingsModel);

        var newPlayerModel = new NewPlayerModel();
        newPlayerController = new NewPlayerController(newPlayerModel);

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

        playerController = new PlayerController(cartController);

        tutorialController = new TutorialController();

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

        playerController = new PlayerController(cartController);
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
                callTutorial();
            }else{
                startGame();
            }
        }else if(gameState.equals(GameState.HIGHSCORE)){
            restartGame();
        }else if(gameState.equals(GameState.TUTORIAL)){
            startGame();
        }
    }

    private void btnRightControl(){
        if(gameState.equals(GameState.GAME)){
            setGate(false);
        }
    }

    private void btnLeftControl(){
        if(gameState.equals(GameState.GAME)){
            setGate(true);
        }
    }


    Function<Runnable, Function0<Unit>> wrapMethod = method -> () -> {
        method.run();
        return Unit.INSTANCE;
    };

}


