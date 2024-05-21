package catchorwaste;

import catchorwaste.controller.GPIOController;
import catchorwaste.controller.TimerController;
import catchorwaste.model.enums.EntityType;
import catchorwaste.model.enums.GameState;
import catchorwaste.model.factories.EntityFactory;
import catchorwaste.view.StartScreenView;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;


import static catchorwaste.controller.CartController.cartMovement;
import static catchorwaste.controller.CartController.onWorkstationCollision;
import static catchorwaste.controller.EndScreenController.initEndscreen;
import static catchorwaste.controller.FallingObjectController.dropObjects;
import static catchorwaste.controller.FallingObjectController.stickToPlayer;
import static catchorwaste.controller.PlayerController.boundaries;
import static catchorwaste.controller.PlayerController.catchObject;
import static catchorwaste.controller.PlayerController.movePlayer;
import static catchorwaste.controller.PunktesystemController.initPunktesystem;
import static catchorwaste.controller.TimerController.initTimer;
import static catchorwaste.controller.TimerController.startTimer;
import static catchorwaste.model.CartModel.setGate;
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

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKeyDown;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKey;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;


public class CatchOrWasteApp extends GameApplication implements TimerController.TimerListener {

    public static Map<String, Image> imageMap;
    public static Map<String, ArrayList<String>> textMap;
    private GameState gameState;

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

        String osArch = System.getProperty("os.arch").toLowerCase();

        if (osArch.contains("arm") || osArch.contains("aarch64")) {
            GPIOController controller = new GPIOController();
            controller.init();

            controller.onAcceptButton(() -> {
                if (gameState.equals(GameState.STARTSCREEN)) {
                    startGame();
                }else if(gameState.equals(GameState.ENDSCREEN)){
                    restartGame();
                }
            });
        }

        onKeyDown(KeyCode.SPACE, "Start Game", () -> {
            if (gameState.equals(GameState.STARTSCREEN)) {
                startGame();
            }else if(gameState.equals(GameState.ENDSCREEN)){
                restartGame();
            }
            return null;
        });

        onKey(KeyCode.RIGHT, "Move Right", () -> {
            movePlayer(true, getGameWorld());
            return null;
        });

        onKey(KeyCode.LEFT, "Move Left", () -> {
            movePlayer(false, getGameWorld());
            return null;
        });

        onKey(KeyCode.DIGIT1, "1", () -> {
            setGate(true);
            return null;
        });

        onKey(KeyCode.DIGIT2, "2", () -> {
            setGate(false);
            return null;
        });


    }

    @Override
    protected void initGame() {
        //load resources
        imageMap = loadImages();
        textMap = loadText();

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
        callStartScreen();
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
    private void startTutorial(){
        //TODO implement Tutorial call here
    }

    private void callStartScreen(){
        removeEverything();
        gameState = GameState.STARTSCREEN;
        StartScreenView.initStartScreenView();
    }

    private void startGame(){
        removeEverything();
        gameState = GameState.GAME;

        spawnEnvironment();
        initPunktesystem();
        initTimer();

        startTimer();
    }

    private void callEndscreen(){
        removeEverything();
        gameState = GameState.ENDSCREEN;
        initEndscreen();
    }

    private void restartGame(){
        gameState = GameState.STARTSCREEN;
        callStartScreen();

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


        map = addToMap("backgrounds", backroundsImgs, map);
        map = addToMap("carts", cartsImgs, map);
        map = addToMap("fallingObjects", fallingObjectsImgs, map);
        map = addToMap("player", playerImgs, map);
        map = addToMap("structures", structuresImgs, map);
        map = addToMap("endScreens", endScreensImgs, map);
        return map;
    }

    public Map<String,ArrayList<String>> loadText(){

        String line;
        String currentTitle="";
        boolean inQuotes = false;
        ArrayList<String> currentList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Map<String,ArrayList<String>> map = new HashMap<>();
        try {
            //BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/config/german.csv"));
            BufferedReader reader = new BufferedReader(new FileReader("/home/pi4j/deploy/german.csv"));

            while ((line = reader.readLine()) != null) {
                if(!line.isEmpty()){
                    if(line.contains("Title: ")){
                        if(currentTitle.isEmpty()){
                            currentTitle = line.substring(7, line.length()-1);
                        }else{
                            map.put(currentTitle, currentList);
                            currentTitle = line.substring(7, line.length()-1);
                            currentList = new ArrayList<>();
                        }

                    }else{
                        sb.append(line);

                        if(line.startsWith("\"")){
                            inQuotes = true;
                        }

                        if (line.length() > 2 && line.charAt(line.length() - 2) == '\"'
                                && line.charAt(line.length() - 1) == ',') {
                            sb.setLength(sb.length() - 1);
                            inQuotes = false;
                            currentList.add(sb.toString());
                            sb = new StringBuilder();
                        }else if(line.length() > 2 && line.endsWith(",") && !inQuotes){
                            sb.setLength(sb.length() - 1);
                            currentList.add(sb.toString());
                            sb = new StringBuilder();
                        }else if(line.length() > 2 && line.endsWith("\"")){
                            currentList.add(sb.toString());
                            inQuotes = false;
                            sb = new StringBuilder();
                        }
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put(currentTitle, currentList);
        System.out.println(map);
        return map;
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
