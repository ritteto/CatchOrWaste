package com.pi4j.example.CatchOrWaste;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.input.KeyCode;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class FxglTest extends GameApplication {


    public static boolean gate;

    Wegwerfpolizist player;
    Cart cart;
    FallingObject fallingObject;

    @Override
    protected void initSettings(GameSettings settings) {
        //settings.setFullScreenAllowed(true);
        //settings.setFullScreenFromStart(true);
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

        for (double i=0; i<0.5;i+=0.12) {
            spawn("HOUSE",getAppWidth()*i,getAppHeight()*0.35);
        }

        //spawn the player from the factory
        Entity playerEntity = spawn("PLAYER",100,getAppHeight()*0.76);
        player = new Wegwerfpolizist(playerEntity);
        cart = new Cart();

        //spawn objects
        Random random = new Random();
        int randomX = random.nextInt(501);

        Entity fallingObject = spawn("ZUFALL", 500, 100);




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
