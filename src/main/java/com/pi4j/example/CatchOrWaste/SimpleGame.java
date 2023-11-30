package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;

public class SimpleGame extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SimpleFactory());
        spawn("OBJECT",200,0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
