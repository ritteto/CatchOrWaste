package catchorwaste;

import catchorwaste.controller.GPIOController;
import catchorwaste.controller.TimerController;
import catchorwaste.model.TimerModel;
import catchorwaste.model.factories.EntityFactory;
import catchorwaste.view.TimerView;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import static catchorwaste.controller.CartController.cartMovement;
import static catchorwaste.controller.CartController.onWorkstationCollision;
import static catchorwaste.controller.FallingObjectController.dropObjects;
import static catchorwaste.controller.FallingObjectController.stickToPlayer;
import static catchorwaste.controller.PlayerController.boundaries;
import static catchorwaste.controller.PlayerController.catchObject;
import static catchorwaste.controller.PlayerController.movePlayer;
import static catchorwaste.model.CartModel.setGate;
import static catchorwaste.model.PunktesystemModel.initPointsMap;
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
import static catchorwaste.view.PunktesystemView.initPunkteSystemView;
import static catchorwaste.view.PunktesystemView.updateScore;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKey;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;


public class CatchOrWasteApp extends GameApplication {

    public static Map<String, Image> imageMap;

    public static void main(String[] args) {
        launch(args);
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

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
        settings.setTicksPerSecond(60);
    }

    @Override
    protected void initInput() {
        String osArch = System.getProperty("os.arch").toLowerCase();

        if (osArch.contains("arm") || osArch.contains("aarch64")) {
            GPIOController controller = new GPIOController();
            controller.init();
        }

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
        getGameScene().setCursorInvisible();

        //generate timer
        TimerModel timerModel = new TimerModel();
        TimerView timerView = new TimerView();
        TimerController timerController = new TimerController(timerModel, timerView);

        // add timer to the game
        getGameScene().addUINode(timerView);
        timerController.startTimer();
        imageMap = new HashMap<>();
        loadImages();
        getGameWorld().addEntityFactory(new EntityFactory());

        Entity background1 = spawn("BACKGROUND", new SpawnData(0, 0).put("Position", 1).put("Name", "background_bad"));
        Entity background2 = spawn("BACKGROUND", new SpawnData(0, 0).put("Position", 2).put("Name", "streets"));
        setBackground(background1);
        setBackground(background2);

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

        initPunktesystem();
        playBackgroundMusic("/home/pi4j/deploy/music.mp3");
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

    @Override
    protected void onUpdate(double tpf) {
        playerOnUpdate(getGameWorld());
        cartOnUpdate(getGameWorld());
        fallingObjectOnUpdate(getGameWorld());
    }

    public void setBackground(Entity entity) {
        ImageView imageView = entity.getViewComponent().getChild(0, ImageView.class);
        imageView.setFitWidth(getAppWidth());
        imageView.setFitHeight(getAppHeight());
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(imageView);
    }

    public void timeIsUp() {
        //TODO: show end screen with results after time is up
        System.out.println("Time's up!");
    }

    public void loadImages() {

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

        addToMap("backgrounds", backroundsImgs);
        addToMap("carts", cartsImgs);
        addToMap("fallingObjects", fallingObjectsImgs);
        addToMap("player", playerImgs);
        addToMap("structures", structuresImgs);
    }

    public void addToMap(String dir, String[] names) {
        for (String s : names) {
            imageMap.put(s, getAssetLoader().loadImage(dir + "/" + s + ".png"));
        }
    }

    public void initPunktesystem() {
        initPunkteSystemView();
        updateScore(0);
        initPointsMap();
        onWorkstationCollision();
    }
}
