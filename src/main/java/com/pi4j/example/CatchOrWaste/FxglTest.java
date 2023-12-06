package com.pi4j.example.CatchOrWaste;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.input.KeyCode;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static com.pi4j.example.CatchOrWaste.Variables.*;

public class FxglTest extends GameApplication {


    public static boolean gate;

    Wegwerfpolizist player;
    Cart cart;
    FallingObject fallingObject;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
    }

    @Override
    protected void initInput() {

        //check for Key Inputs
        onKey(KeyCode.RIGHT,"Move Right", ()-> player.move("Right"));

        onKey(KeyCode.LEFT,"Move Left", ()-> player.move("Left"));

        onKeyDown(KeyCode.G,"G",()-> spawn("CART",getAppWidth()*0.77,getAppHeight()*0.78));


        onKey(KeyCode.DIGIT1,"1", ()-> gate = true);

        onKey(KeyCode.DIGIT2,"2", ()-> gate = false);

        onKey(KeyCode.F,"F", ()-> spawn("MARTIN",100,100));



    }

    @Override
    protected void initGame() {

        getGameWorld().addEntityFactory(new SimpleFactory());

        Entity background1 = spawn("BACKGROUND",0,0);
        Entity background2 = spawn("BACKGROUND",0,0);
        setBackground(background1, "background_bad.png");
        setBackground(background2, "streets.png");

        // spawn housses
        for (double i=0.135; i<0.635;i+=0.12) {
            spawn("HOUSE",getAppWidth()*i,getAppHeight()*0.0001);
        }

        // spawn market, repaicenter & recycling
        spawn("REPAIR", getAppWidth()*0.837, getAppHeight()*0.1);
        spawn("MARKET", getAppWidth()*0.73, getAppHeight()*0.1);
        spawn("RECYCLING", getAppWidth()*-0.018, getAppHeight()*0.48);

        //spawn the player from the factory
        Entity playerEntity = spawn("PLAYER",100,getAppHeight()*0.8);
        player = new Wegwerfpolizist(playerEntity);
        cart = new Cart();


        //spawn objects
        Entity fallingObject_entity = spawn("OBJECT", 500, 100);
        fallingObject = new FallingObject(fallingObject_entity);


    }

    @Override
    protected void onUpdate(double tpf) {

        if(player != null){
            player.playerOnUpdate(cart,getGameWorld());
        }

        if(cart != null){
            cart.onUpdate(getGameWorld());
        }

        if(fallingObject != null){
            fallingObject.onUpdate(getGameWorld());
        }

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

}
