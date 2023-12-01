package CatchOrWaste;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.pi4j.example.CatchOrWaste.SimpleFactory;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class Test1 extends GameApplication {
    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
    }

    public void initGame() {
        FXGL.getGameWorld().addEntityFactory(new SimpleFactory());
        player = spawn("OBJECT", getAppWidth()/2, getAppHeight()/2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
