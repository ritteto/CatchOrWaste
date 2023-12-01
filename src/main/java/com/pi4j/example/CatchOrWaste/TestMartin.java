package com.pi4j.example.CatchOrWaste;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.input.KeyCode;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class TestMartin extends GameApplication {

    private Entity kleid, polizist, apfel, zufall;

    private static final double PLAYERSIZE=3000*0.035;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setHeight(1000);
        gameSettings.setWidth(1000);
    }

    public void initGame(){
        getGameWorld().addEntityFactory(new SimpleFactory());
        Random random = new Random(getAppWidth());
        int random1 = random.nextInt(getAppWidth());
        int random2 = random.nextInt(getAppWidth());
        polizist = spawn("PLAYER", getAppWidth() /2, getAppHeight() - 100);
        zufall = spawn("ZUFALL", getRandomX() + PLAYERSIZE, 0);
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.LEFT, "move left", () -> {
            polizist.setX(polizist.getX() - 20);
            polizist.getViewComponent().clearChildren();
            polizist.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("wegwerfpolizist_l.png")));
        });

        onKey(KeyCode.RIGHT, "move right", () -> {
            polizist.setX(polizist.getX() + 20);
            polizist.getViewComponent().clearChildren();
            polizist.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("wegwerfpolizist_r.png")));
        });

        onKey(KeyCode.UP, "move up", () -> {
            polizist.setY(polizist.getY() - 20);
        });

        onKey(KeyCode.DOWN, "move down", () -> {
            polizist.setY(polizist.getY() + 20);
        });
    }

        @Override
        protected void onUpdate(double tpf) {
            if(getPlayer().getX()>getAppWidth() - PLAYERSIZE){
                getPlayer().setX(getAppWidth() - PLAYERSIZE);
            }else if (getPlayer().getX()==getAppWidth()) {
                changeDirection("wegwerfpolizist_l.png");
            }
            else if(getPlayer().getX()<0){
                getPlayer().setX(0);
            }

            if(getPlayer().getY()>getAppHeight() - PLAYERSIZE){
                getPlayer().setY(getAppHeight() - PLAYERSIZE);
            }else if (getPlayer().getY()==getAppHeight()) {
                changeDirection("wegwerfpolizist_l.png");
            }

            if(zufall.getY() >= 400){
                zufall.removeFromWorld();
                zufall = spawn("ZUFALL", getRandomX() + PLAYERSIZE,0);
            }


        }


    private static Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }

    private static Entity getApfel() {
        return getGameWorld().getSingleton(EntityType.APFEL);
    }

    private static Entity getObject(){
        return getGameWorld().getSingleton(EntityType.OBJECT);
    }

    private static Entity getZufall(){
        return getGameWorld().getSingleton(EntityType.ZUFALL);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void changeDirection(String view){
        ViewComponent viewComponent = getPlayer().getComponent(ViewComponent.class);
        Texture texture = new Texture(getAssetLoader().loadImage(view));
        viewComponent.clearChildren();
        viewComponent.addChild(texture);
    }

    public int getRandomX(){
        Random random = new Random();
        return random.nextInt(getAppWidth());
    }
}



