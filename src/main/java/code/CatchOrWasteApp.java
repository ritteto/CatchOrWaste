package code;

import code.model.factories.EntityFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.input.KeyCode;


import static code.controller.CartController.cartMovement;
import static code.controller.FallingObjectController.dropObjects;
import static code.controller.FallingObjectController.stickToPlayer;
import static code.controller.PlayerController.*;
import static code.model.CartModel.setGate;
import static code.model.Constants.Constants.*;
import static code.view.FallingObjectView.spawnObjects;
import static code.view.PlayerView.isAtStreetEnd;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;


public class CatchOrWasteApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        // settings.setFullScreenAllowed(true);
        // settings.setFullScreenFromStart(true);
    }

    @Override
    protected void initInput() {

        //check for Key Inputs
        onKey(KeyCode.RIGHT,"Move Right", ()-> movePlayer(true, getGameWorld()));

        onKey(KeyCode.LEFT,"Move Left", ()-> movePlayer(false, getGameWorld()));

        onKey(KeyCode.DIGIT1,"1", ()-> setGate(true));

        onKey(KeyCode.DIGIT2,"2", ()-> setGate(false));

    }

    @Override
    protected void initGame() {

        getGameWorld().addEntityFactory(new EntityFactory());

        Entity background1 = spawn("BACKGROUND",0,0);
        Entity background2 = spawn("BACKGROUND",0,0);
        setBackground(background1, "backgrounds/background_bad.png");
        setBackground(background2, "backgrounds/streets.png");

        // spawn housses
        spawn("HOUSE",new SpawnData(HOUSE1_X,HOUSE_Y).put("Position", 1));
        spawn("HOUSE",new SpawnData(HOUSE2_X,HOUSE_Y).put("Position", 2));
        spawn("HOUSE",new SpawnData(HOUSE3_X,HOUSE_Y).put("Position", 1));
        spawn("HOUSE",new SpawnData(HOUSE4_X,HOUSE_Y).put("Position", 2));


        // spawn market, repaicenter & recycling
        spawn("WORKSTATION", new SpawnData(getAppWidth()*0.837, getAppHeight()*0.1).put("Position",1));
        spawn("WORKSTATION", new SpawnData(getAppWidth()*0.73, getAppHeight()*0.1).put("Position",2));
        spawn("WORKSTATION", new SpawnData(getAppWidth()*-0.0185, getAppHeight()*0.48).put("Position",3));

        //spawn the player from the factory
        spawn("PLAYER",100,getAppHeight()*0.73);
    }

    @Override
    protected void onUpdate(double tpf) {
        playerOnUpdate(getGameWorld());
        cartOnUpdate(getGameWorld());
        fallingObjectOnUpdate(getGameWorld());
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void setBackground(Entity entity, String view){
        Texture textureFromView = new Texture(getAssetLoader().loadImage(view));
        textureFromView.setFitWidth(getAppWidth());
        textureFromView.setFitHeight(getAppHeight());
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(textureFromView);
    }

    public static void fallingObjectOnUpdate(GameWorld gameWorld){
        spawnObjects(gameWorld);
        dropObjects(gameWorld);
        stickToPlayer(gameWorld);
    }

    public static void cartOnUpdate(GameWorld gameWorld){
        cartMovement(gameWorld);
    }

    public static void playerOnUpdate(GameWorld gameWorld){
        catchObject(gameWorld);
        boundaries(gameWorld);
        isAtStreetEnd(gameWorld);
    }


}
