package catchorwaste;

import catchorwaste.controller.GPIOController;
import catchorwaste.controller.TimerController;
import catchorwaste.model.PunktesystemModel;
import catchorwaste.model.TimerModel;
import catchorwaste.model.factories.EntityFactory;
import catchorwaste.view.PunktesystemView;
import catchorwaste.view.TimerView;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

import static catchorwaste.controller.CartController.cartMovement;
import static catchorwaste.controller.FallingObjectController.dropObjects;
import static catchorwaste.controller.FallingObjectController.stickToPlayer;
import static catchorwaste.controller.PlayerController.boundaries;
import static catchorwaste.controller.PlayerController.catchObject;
import static catchorwaste.controller.PlayerController.movePlayer;
import static catchorwaste.model.CartModel.setGate;
import static catchorwaste.model.constants.Constants.HOUSE_Y;
import static catchorwaste.model.constants.Constants.HOUSE1_X;
import static catchorwaste.model.constants.Constants.HOUSE2_X;
import static catchorwaste.model.constants.Constants.HOUSE3_X;
import static catchorwaste.model.constants.Constants.HOUSE4_X;
import static catchorwaste.view.FallingObjectView.spawnObjects;
import static catchorwaste.view.PlayerView.isAtStreetEnd;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;


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
        catchObject(gameWorld);
        boundaries(gameWorld);
        isAtStreetEnd(gameWorld);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
        settings.setTicksPerSecond(30);
    }

    @Override
    protected void initInput() {
        String osArch = System.getProperty("os.arch").toLowerCase();

        if (osArch.contains("arm") || osArch.contains("aarch64")) {
            GPIOController controller = new GPIOController(); // Funktioniert nur bei ARM Prozessoren (Raspberry Pi)
        }

        onKey(KeyCode.RIGHT, "Move Right", () -> movePlayer(true, getGameWorld()));
        onKey(KeyCode.LEFT, "Move Left", () -> movePlayer(false, getGameWorld()));
        onKey(KeyCode.DIGIT1, "1", () -> setGate(true));
        onKey(KeyCode.DIGIT2, "2", () -> setGate(false));

    }

    @Override
    protected void initGame() {
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

        Entity background1 = spawn("BACKGROUND", new SpawnData(0, 0).put("Position", 1));
        Entity background2 = spawn("BACKGROUND", new SpawnData(0, 0).put("Position", 2));
        setBackground(background1);
        setBackground(background2);

        // spawn houses
        spawn("HOUSE", new SpawnData(HOUSE1_X, HOUSE_Y).put("Position", 1));
        spawn("HOUSE", new SpawnData(HOUSE2_X, HOUSE_Y).put("Position", 2));
        spawn("HOUSE", new SpawnData(HOUSE3_X, HOUSE_Y).put("Position", 1));
        spawn("HOUSE", new SpawnData(HOUSE4_X, HOUSE_Y).put("Position", 2));


        // spawn market, repaicenter & recycling
        spawn("WORKSTATION", new SpawnData(getAppWidth() * 0.837, getAppHeight() * 0.1).put("Position", 1));
        spawn("WORKSTATION", new SpawnData(getAppWidth() * 0.73, getAppHeight() * 0.1).put("Position", 2));
        spawn("WORKSTATION", new SpawnData(getAppWidth() * -0.0185, getAppHeight() * 0.48).put("Position", 3));

        //spawn the player from the factory
        spawn("PLAYER", 100, getAppHeight() * 0.73);

        PunktesystemModel scoreModel = new PunktesystemModel();
        PunktesystemView scoreView = new PunktesystemView();
        // add score system to the game
        getGameScene().addUINode(scoreView);
        scoreView.updateScore(scoreModel.getScore());

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

        var backroundsImgs = new String[]{"background_bad", "streets"};

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

}
